import {useState} from "react";
import {createNote} from "./NoteAppService.tsx";
import {Note} from 'Note.ts'


export const Notes = () => {
const [note, setNote] = useState<Note[]>([]);
const [newNote, setNewNote] = useState('');

function handleClick() {
    createNote(newNote).then()
    }

    return(
        <div>
            <div className='heading'>
                <h1> Your Daily Notes</h1>
            </div>
            <label className="box-content size-32 border-4 p-4 ...">
                <input
                    type='text'
                    name='text'
                    placeholder="Note"
                    value={newNote}
                    onChange={(event)=> setNewNote(event.target.value)}
                    id='text'>

                </input>
            </label>
            <button onClick={handleClick}>Add New</button>
            <button>Edit Note</button>
            <button>Delete Note</button>

        </div>
    )
}