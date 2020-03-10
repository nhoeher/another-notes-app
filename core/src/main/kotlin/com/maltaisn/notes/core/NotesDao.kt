/*
 * Copyright 2020 Nicolas Maltais
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.maltaisn.notes.core

import androidx.room.*
import com.maltaisn.notes.core.entity.Note
import com.maltaisn.notes.core.entity.NoteStatus


@Dao
interface NotesDao {

    @Insert
    suspend fun insert(note: Note): Long

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM notes WHERE id == :id")
    suspend fun getById(id: Long): Note?

    @Query("SELECT * FROM notes WHERE status == :status ORDER BY modified_date DESC")
    suspend fun getByStatus(status: NoteStatus): List<Note>

    @Query("SELECT * FROM notes WHERE id IN (SELECT rowid FROM notes_fts WHERE notes_fts MATCH :query) ORDER BY modified_date DESC")
    suspend fun search(query: String): List<Note>

    @Query("SELECT * FROM notes WHERE modified_date = (SELECT MAX(modified_date) FROM notes) ORDER BY id DESC LIMIT 1")
    suspend fun getLastModified(): Note?

}
