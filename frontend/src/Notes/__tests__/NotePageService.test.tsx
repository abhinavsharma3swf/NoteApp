import {afterAll, afterEach, beforeAll, describe, expect, it } from "vitest";
import {Notes} from "../Note.ts";
import {http, HttpResponse} from "msw";
import {setupServer} from "msw/node";
import axios from "axios";
import {createNote} from "../NoteAppService.tsx";

describe('NotePageService', () => {

    axios.defaults.baseURL = "http://localhost:3000"
    const server = setupServer()
    beforeAll(()=>server.listen({onUnhandledRequest: 'error'}))
    afterAll(() => server.close())
    afterEach(() => server.resetHandlers())

    it('should send a request to add new note', async () => {
        const expected: Notes = {
            id: 1,
            text: 'Hello this is my new note'
        }
        server.use(http.post('/api/noteapp',()=>
            HttpResponse.json(expected, {status: 200})
        ))
            expect(await createNote('new task')).toStrictEqual(expected);
        });
});