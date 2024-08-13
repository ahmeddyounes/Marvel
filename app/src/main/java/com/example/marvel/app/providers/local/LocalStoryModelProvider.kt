package com.example.marvel.app.providers.local

import androidx.lifecycle.LiveData
import com.example.marvel.app.database.dao.StoryAssociationDao
import com.example.marvel.app.database.dao.StoryDao
import com.example.marvel.app.database.models.Story
import com.example.marvel.app.database.models.StoryAssociation
import com.example.marvel.app.providers.AbstractStoryModelProvider

class LocalStoryModelProvider(
    private val dao: StoryDao,
    private val associationDao: StoryAssociationDao,
) : AbstractStoryModelProvider() {
    override fun save(story: Story) {
        try {
            dao.insert(story)
        } catch (_: Exception) {

        }
    }

    override fun delete(story: Story) {
        dao.delete(story)
    }

    override fun saveAssociation(storyAssociation: StoryAssociation) {
        try {
            associationDao.insert(storyAssociation)
        } catch (_: Exception) {

        }
    }

    override fun deleteAssociation(storyAssociation: StoryAssociation) {
        associationDao.delete(storyAssociation)
    }

    override fun getById(id: Int): LiveData<Story?> {
        return dao.getById(id)
    }

    override fun getAll(limit: Int, offset: Int): LiveData<List<Story>> {
        return dao.getAll(limit, offset)
    }

    override fun getAssociated(id: Int, limit: Int, offset: Int): LiveData<List<Story>> {
        return dao.getAssociatedByCharacter(id, limit, offset)
    }
}