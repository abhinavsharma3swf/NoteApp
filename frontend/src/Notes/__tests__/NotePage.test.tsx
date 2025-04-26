import {describe, expect, it, vi} from "vitest";
import {render, screen} from "@testing-library/react";
import {Notes} from "../Notes.tsx";
import * as NoteAppService from '../NoteAppService.tsx'
import {userEvent} from "@testing-library/user-event";

describe('NoteApp', () => {

    it('should display the Notes App page and the form', () => {
        render(<Notes/>)
        expect(screen.getByRole('heading', {name: /Your Daily Notes/i} )).toBeVisible()
        expect(screen.getByLabelText('form')).toBeVisible()
    })

    it('should have an add button for new notes, it should have a delete button and it should have an edit button', () =>{
        render(<Notes/>)
        expect(screen.getByRole('button', {name: /Add New/i})).toBeVisible()
        expect(screen.getByRole('button', {name: /Delete Note/i})).toBeVisible()
        expect(screen.getByRole('button', {name: /Delete Note/i})).toBeVisible()
    })

    it('should display the text input box, date, importance and the completion', async () => {
        render(<Notes/>)
        const textInput = screen.getByPlaceholderText('Input your notes');
        expect(textInput).toBeVisible();
        expect(screen.getByPlaceholderText('Importance number')).toBeVisible();
        expect(screen.getByPlaceholderText('Completion Status')).toBeVisible();
    })



    it('should add the new note', async () => {
        const savedNote = {
                id: null,
            date: expect.any(Date),
            text: "Empty",
            importance: 0,
            completion: 0
            };
        const mockCreateNote = vi.spyOn(NoteAppService, 'createNote')
            .mockResolvedValueOnce(savedNote)
        render(<Notes/>);
        const textInput = screen.getByPlaceholderText('Input your notes');
        expect(textInput).toBeVisible();
        const addButton = screen.getByRole('button', {name: /add/i})
        await userEvent.type(textInput, "Empty");
        await userEvent.click(addButton);
        expect(mockCreateNote).toHaveBeenLastCalledWith(savedNote);
    });

    it('should display all of the notes', async () => {
        const viewNote = [{
                id: null,
                date: new Date("2025-02-04"),
                text: "Empty",
                importance: 0,
                completion: 0
            }];
        const mockViewNote = vi.spyOn(NoteAppService, 'fetchNote').mockResolvedValueOnce(viewNote)
        render(<Notes/>)
        const viewButton = screen.getByRole('button', {name: /View All Note/i})
        await userEvent.click(viewButton)
        expect(mockViewNote).toHaveBeenCalledOnce();
    });


});


