
import {createNote, fetchNote} from "./NoteAppService.tsx";
import {Note} from './Note.ts';
import {useState} from "react";


export const Notes = () => {
const [note, setNote] = useState<Note[]>([]);
const [newNote, setNewNote] = useState<Omit<Note, "id">>({
    text: "",
    date: new Date(),
    importance: 0,
    completion: 0,
});


function fetch() {
    fetchNote().then(setNote)
}

function handleClick(e:any) {
    e.preventDefault()
    console.log("Sending note:", newNote);
    const noteswithId: Note = {...newNote, id: null};
    setNote([noteswithId]);
    // setNewNote({text: "", date: new Date(), completion: 0, importance: 0})
    createNote(newNote.text, new Date().getTime()/1000, newNote.importance, newNote.completion).then(() => {
        fetch();
        })
    }

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
                        id='completion'
                        type='number'
                        className='formInputStyle'
                        value={newNote.completion}
                        placeholder='Completion Status'
                        onChange={(e) => setNewNote({...newNote, completion: Number(e.target.value)})}
                    />
                </label>

                <button type={"submit"}>Add New</button>
            </form>


            <button>Edit Note</button>
            <button>Delete Note</button>
            <button onClick={fetch}>View All Note</button>

            <p>
                {note.map((el, i) => {return <span key={i}>{el.text}</span>})}
            </p>

        </div>
    )
}

