package cn.houlinan.mylife.entity.primary.repository;

import cn.houlinan.mylife.entity.BaseJpaRepository;
import cn.houlinan.mylife.entity.Note;
import cn.houlinan.mylife.entity.Team;

import java.io.Serializable;

public interface NoteRepository extends BaseJpaRepository<Note, Serializable> {

    public Note findNoteById(String id );

}
