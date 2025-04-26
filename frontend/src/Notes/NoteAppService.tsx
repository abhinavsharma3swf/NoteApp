import {Note} from "./Note.ts";
import axios, {AxiosResponse} from "axios";


type CreateNote = (note: Note) => Promise<Note>
type FetchNote = () => Promise<Note[]>;
type DeleteNote = (id: number | null) => Promise<String>
type EditNote = (id: number | null) => Promise<String>

export const createNote: CreateNote = (note: Note) => {
    // const newDate = new Date().getTime()/1000;
// date: new Date().getTime()/1000, importance: 0, completion: 0

    const options =
        {
            method: 'POST',
            url: 'http://localhost:8080/api/noteapp',
            headers: {
                accept: 'application/json'
            },
            data: {
                text: note.text,
                date: note.date.getTime() / 1000,
                importance: note.importance,
                complete: note.completion
            }
        }

    return axios(options)
        .then((r: AxiosResponse<Note>) => r.data)
};

export const fetchNote: FetchNote = () => (
    axios.get('http://localhost:8080/api/noteapp')
        .then((r: AxiosResponse<Note[]>) => r.data)
);

export const deleteNote: DeleteNote = (id: number | null) => (
    axios.delete(`http://localhost:8080/api/noteapp/${id}`)
        .then((r: AxiosResponse<String>) => r.data)
);

export const editNote: EditNote = (id) => (
    axios.put(`http://localhost:8080/api/noteapp/${id}`)
        .then((r: AxiosResponse<String>) => r.data)
)