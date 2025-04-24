
import {createNote} from "./NoteAppService.tsx";
import {Note} from './Note.ts';
import {useState} from "react";


export const Notes = () => {
const [note, setNote] = useState<Note[]>([]);
const [newNote, setNewNote] = useState('');




function handleClick() {
    createNote(newNote).then()
    }

    return(
        <div>
            <div className="flex-box space-x-2">
                <h3 className="mb-2 text-lg font-semibold">Your Daily Notes</h3>
            </div>
            <div className="max-w-md mx-auto mt-6 p-6 bg-white rounded-2xl shadow-lg">
            <label className="box-content size-32 border-4 p-4 ...">
                <textarea
                    name='text'
                    placeholder="Note"
                    value={newNote}
                    onChange={(event)=> setNewNote(event.target.value)}
                    id='text'>
                </textarea>
            </label>
            </div>
            <button onClick={handleClick}>Add New</button>
            <button>Edit Note</button>
            <button>Delete Note</button>

        </div>
    )
}