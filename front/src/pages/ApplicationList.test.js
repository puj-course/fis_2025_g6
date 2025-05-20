import React from 'react';
import { render, screen, waitFor, fireEvent } from '@testing-library/react';
import ApplicationListPage from '../pages/ApplicationList';
import axios from 'axios';
import { MemoryRouter } from 'react-router-dom';

global.MutationObserver = class {
    constructor(callback) { }
    disconnect() { }
    observe() { }
    takeRecords() { return []; }
};

// Mock axios
jest.mock('axios');

// Mock función translateApplicationStatus
jest.mock('../util/Util', () => ({
    translateApplicationStatus: (status) => {
        const map = {
            PENDING: 'Pendiente',
            APPROVED: 'Aprobada',
            REJECTED: 'Rechazada',
        };
        return map[status] || status;
    },
}));

describe('ApplicationListPage', () => {
    beforeEach(() => {
        localStorage.setItem('token', 'fake-token');
        jest.clearAllMocks();
    });

    test('muestra mensaje cuando no hay solicitudes', async () => {
        axios.get.mockResolvedValueOnce({ data: [] });

        render(
            <MemoryRouter>
                <ApplicationListPage />
            </MemoryRouter>
        );

        // Esperar que el mensaje aparezca
        await waitFor(() => {
            expect(screen.getByText(/no tienes solicitudes registradas/i)).toBeInTheDocument();
        });
    });

    test('muestra la lista de solicitudes', async () => {
        const mockApplications = [
            {
                id: 1,
                date: '2025-05-18T12:00:00Z',
                status: 'PENDING',
                pet: { name: 'Firulais', species: 'Perro' },
            },
            {
                id: 2,
                date: '2025-05-17T10:00:00Z',
                status: 'APPROVED',
                pet: { name: 'Misu', species: 'Gato' },
            },
        ];

        axios.get.mockResolvedValueOnce({ data: mockApplications });

        render(
            <MemoryRouter>
                <ApplicationListPage />
            </MemoryRouter>
        );

        // Esperar que se muestren ambas solicitudes
        for (const app of mockApplications) {
            await waitFor(() => {
                expect(screen.getByText(new RegExp(`Solicitud #${app.id}`))).toBeInTheDocument();
                expect(screen.getByText(new RegExp(app.pet.name))).toBeInTheDocument();
                expect(screen.getByText(new RegExp(app.pet.species))).toBeInTheDocument();
            });
        }

        // También verificar que se traduzca el estado
        expect(screen.getByText(/Pendiente/)).toBeInTheDocument();
        expect(screen.getByText(/Aprobada/)).toBeInTheDocument();

        // Botón Cancelar solo debe aparecer para la solicitud con status PENDING
        const cancelButtons = screen.getAllByRole('button', { name: /cancelar/i });
        expect(cancelButtons).toHaveLength(1);
    });

    test('permite cancelar una solicitud y la elimina de la lista', async () => {
        const mockApplications = [
            {
                id: 1,
                date: '2025-05-18T12:00:00Z',
                status: 'PENDING',
                pet: { name: 'Firulais', species: 'Perro' },
            },
        ];

        axios.get.mockResolvedValueOnce({ data: mockApplications });
        axios.delete.mockResolvedValueOnce({});

        render(
            <MemoryRouter>
                <ApplicationListPage />
            </MemoryRouter>
        );

        // Esperar que el botón Cancelar esté en el documento
        const cancelButton = await screen.findByRole('button', { name: /cancelar/i });
        expect(cancelButton).toBeInTheDocument();

        // Hacer click en cancelar
        fireEvent.click(cancelButton);

        // Esperar que axios.delete haya sido llamado con la URL correcta
        await waitFor(() => {
            expect(axios.delete).toHaveBeenCalledWith('http://localhost:8080/solicitudes/1', expect.any(Object));
        });

        // La solicitud debe desaparecer de la UI
        await waitFor(() => {
            expect(screen.queryByText(/Solicitud #1/)).not.toBeInTheDocument();
            expect(screen.getByText(/no tienes solicitudes registradas/i)).toBeInTheDocument();
        });
    });
});
