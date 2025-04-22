import {Notes} from "./Note.ts";
import axios, {AxiosResponse} from "axios";

type CreateNote = (text: string) => Promise<Notes>

export const createNote: CreateNote = (data: string) => (
    axios.post('/api/noteapp', {id: null, text: data})
        .then((r: AxiosResponse<Notes>)=> r.data)
);
