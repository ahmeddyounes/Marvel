package com.example.marvel.app.providers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.marvel.app.database.models.Story
import com.example.marvel.app.database.models.StoryAssociation
import com.example.marvel.app.providers.local.LocalStoryModelProvider
import com.example.marvel.app.providers.network.NetworkStoryModelProvider

class StoryModelProvider(
    private val local: LocalStoryModelProvider,
    private val network: NetworkStoryModelProvider
) : AbstractStoryModelProvider() {
    override fun getById(id: Int): LiveData<Story?> {
        return MediatorLiveData<Story?>().apply {
            addSource(local.getById(id)) { result ->
                if (result != null) {
                    value = result
                } else {
                    addSource(network.getById(id)) { networkResult ->
                        if (networkResult != null) {
                            local.save(networkResult)
                        }
                        value = networkResult
                    }
                }
            }
        }
    }

    override fun getAll(limit: Int, offset: Int): LiveData<List<Story>> {
        return MediatorLiveData<List<Story>>().apply {
            addSource(network.getAll(limit, offset)) { result ->
                if (result.isNotEmpty()) {
                    store(result)
                    value = result
                } else {
                    addSource(local.getAll(limit, offset)) { localResult ->
                        value = localResult
                    }
                }
            }
        }
    }

    override fun getAssociated(id: Int, limit: Int, offset: Int): LiveData<List<Story>> {
        return MediatorLiveData<List<Story>>().apply {
            addSource(network.getAssociated(id, limit, offset)) { result ->
                if (result.isNotEmpty()) {
                    storeAssociation(id, result)
                    value = result
                } else {
                    addSource(local.getAssociated(id, limit, offset)) { localResult ->
                        value = localResult
                    }
                }
            }
        }
    }

    private fun storeAssociation(id: Int, stories: List<Story>) {
        for (item in stories) {
            saveAssociation(StoryAssociation(character = id, story = item.id))
        }
    }

    private fun store(stories: List<Story>) {
        for (item in stories) {
            save(item)
        }
    }

    override fun save(story: Story) {
        local.save(story)
    }

    override fun delete(story: Story) {
        local.delete(story)
    }

    override fun saveAssociation(storyAssociation: StoryAssociation) {
        local.saveAssociation(storyAssociation)
    }

    override fun deleteAssociation(storyAssociation: StoryAssociation) {
        local.deleteAssociation(storyAssociation)
    }
}