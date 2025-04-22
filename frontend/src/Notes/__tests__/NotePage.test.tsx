import {describe, expect, it, vi} from "vitest";
import {render, screen} from "@testing-library/react";
import {Notes} from "../Notes.tsx";
import * as NoteAppService from '../NoteAppService.tsx'
import {userEvent} from "@testing-library/user-event";

describe('NoteApp', () => {

    it('should display the Notes App page', () => {
        render(<Notes/>)
        expect(screen.getByRole('heading', {name: /Your Daily Notes/i} )).toBeVisible()
    })

    it('should have an add button for new notes, it should have a delete button and it should have an edit button', () =>{
        render(<Notes/>)
        expect(screen.getByRole('button', {name: /Add New/i})).toBeVisible()
        expect(screen.getByRole('button', {name: /Delete Note/i})).toBeVisible()
        expect(screen.getByRole('button', {name: /Delete Note/i})).toBeVisible()
    })

    it('should display the text input box', async () => {
        render(<Notes/>)
        const textInput = screen.getByPlaceholderText('Note');
        expect(textInput).toBeVisible();
    })



    it('should add the new note', async () => {
        const savedNote = 'Hello this is my new note';
        const mockCreateNote = vi.spyOn(NoteAppService, 'createNote')
            .mockResolvedValueOnce({id: 1, text: savedNote})
        render(<Notes/>);
        const textInput = screen.getByPlaceholderText('Note');
        expect(textInput).toBeVisible();
        const addButton = screen.getByRole('button', {name: /add/i})
        await userEvent.type(textInput, savedNote);
        await userEvent.click(addButton);
        expect(mockCreateNote).toHaveBeenLastCalledWith(savedNote);
    });
});


