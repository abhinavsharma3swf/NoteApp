
import {createNote, deleteNote, fetchNote} from "./NoteAppService.tsx";
import {Note} from './Note.ts';
import {useEffect, useState} from "react";




export const Notes = () => {
const [note, setNote] = useState<Note[]>([]);
const [newNote, setNewNote] = useState<Note>({
    id: null,
    text: "",
    date: new Date(),
    importance: 0,
    completion: 0,
});



function fetch() {
    fetchNote().then(setNote)
    console.log(note);
}

function handleClick(e:any) {
    e.preventDefault()
    console.log("Sending note:", newNote);
    createNote(newNote).then(() => fetch())
}

const handleDelete = (id: number) => {
if (id === null) return; // Avoid trying to delete without a valid ID
deleteNote(id).then(() => fetch());
console.log(id);
};




return(
        <div>
            <h1>Your Daily Notes </h1>
            <form onSubmit={handleClick} className="from" aria-label='form' method='post' id='form'>
            <label id='date'>
            <input
                type='date'
                   value={newNote.date.toISOString().split('T')[0]}
                onChange={(e) => setNewNote({...newNote, date: new Date(e.target.value)})}
            />
            </label>
                <label id='text'>
                    <textarea
                        placeholder='Input your notes'
                        value={newNote.text}
                        onChange={(e) => setNewNote({...newNote, text: e.target.value})}
                    />

                    <input
                        type='number'
                        placeholder='Importance number'
                        value={newNote.importance}
                        onChange={(e) => setNewNote({...newNote, importance: Number(e.target.value)})}
                    />

                    <input
                        type='number'
                        className='formInputStyle'
                        value={newNote.completion}
                        placeholder='Completion Status'
                        onChange={(e) => setNewNote({...newNote, completion: Number(e.target.value)})}
                    />
                </label>
                <button type={"submit"}>Add New</button>
            </form>
            <button onClick={fetch}>View All Note</button>
            <button>Edit Note</button>


            <table>
                <thead>
                <tr>
                    <th>ID </th>
                    <th> Notes</th>
                    <th> Importance</th>
                    <th> Completion</th>
                </tr>
                </thead>
                <tbody>
                {note.map((el, i)=> (
                    <tr>
                    <td key={i}> {el.id}</td>
                    <td>{el.text}</td>
                    <td>{el.importance}</td>
                    <td>{el.completion}</td>
                    <td><button onClick={() => handleDelete(el.id)}>Delete Note</button></td>
                </tr>))}
                </tbody>
            </table>


                        {/*<td>{note.map((element, index) => {return <span key={index}>{element.date}</span>})}*/}
                        {/*</td>*/}











        </div>
    )
}

