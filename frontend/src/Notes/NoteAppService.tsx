import {Note} from "./Note.ts";
import axios, {AxiosResponse} from "axios";

type CreateNote = (text: string) => Promise<Note>
type FetchNote = () => Promise<Note[]>;
type DeleteNote = (id: number | null) => Promise<String>
type EditNote = (id: number | null) => Promise<String>

export const createNote: CreateNote = (data: string) => (
    axios.post('http://localhost:8080/api/noteapp', {id: null, text: data})
        .then((r: AxiosResponse<Note>)=> r.data)
);

export const fetchNote: FetchNote = () => (
    axios.get('http://localhost:8080/api/noteapp')
        .then((r: AxiosResponse<Note[]>)=>r.data)
);

export const deleteNote: DeleteNote = (id: number | null) => (
    axios.delete(`http://localhost:8080/api/noteapp/${id}`)
        .then((r: AxiosResponse<String>)=> r.data)
);

export const editNote: EditNote = (id) => (
    axios.put(`http://localhost:8080/api/noteapp/${id}`)
        .then((r: AxiosResponse<String>) => r.data)
)