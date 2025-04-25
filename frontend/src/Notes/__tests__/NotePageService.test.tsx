import {afterAll, afterEach, beforeAll, describe, expect, it} from "vitest";
import {Note} from "../Note.ts";
import {http, HttpResponse} from "msw";
import {setupServer} from "msw/node";
import axios from "axios";
import {createNote, deleteNote, editNote, fetchNote} from "../NoteAppService.tsx";

describe('NotePageService', () => {

    axios.defaults.baseURL = "http://localhost:8080"
    const server = setupServer()
    beforeAll(()=>server.listen({onUnhandledRequest: 'error'}))
    afterAll(() => server.close())
    afterEach(() => server.resetHandlers())

    it('should send a request to add new note', async () => {
        const expected: Note = {
            id: 1,
            text: 'Hello this is my new note',
            date: new Date(),
            importance: 5,
            completion: 30
        }
        server.use(http.post('http://localhost:8080/api/noteapp',()=>
            HttpResponse.json(expected, {status: 200})
        ))
            expect(await createNote(expected)).toStrictEqual(JSON.parse(JSON.stringify(expected)));
        });

    it('should send a request to fetch all the notes', async () => {
        const expectedNotes: Note[] = [
            {id: 1, text: "First note", date: new Date(), completion: .30, importance: 5},
            {id: 2, text: "second note", date: new Date(), completion: .30, importance: 5}
        ];
        server.use(http.get('http://localhost:8080/api/noteapp',() =>
        HttpResponse.json(expectedNotes, {status: 200})
        ))
        expect(await fetchNote()).toStrictEqual(expectedNotes)
    });

    it('should send a request to delete the note', async () => {
        server.use(http.delete(`http://localhost:8080/api/noteapp/1`, () =>
        HttpResponse.json('Note deleted', {status: 200})
        ))
        expect(await deleteNote(1)).toStrictEqual('Note deleted')
    });

    it('should send a request to edit a note', async () => {
        server.use(http.put('http://localhost:8080/api/noteapp/1', () =>
        HttpResponse.json('Note edited', {status: 200})
        ))
        expect(await editNote(1)).toStrictEqual('Note edited')
    });
});