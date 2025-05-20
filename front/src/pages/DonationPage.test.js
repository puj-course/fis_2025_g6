import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom'; // ⬅️ Importa MemoryRouter
import DonationsPage from './DonationsPage';
import axios from 'axios';

global.MutationObserver = class {
    constructor(callback) { }
    disconnect() { }
    observe() { }
    takeRecords() { return []; }
};

jest.mock('axios');
describe('DonationsPage', () => {
    it('muestra mensaje cuando no hay donaciones', async () => {
        axios.get.mockResolvedValue({ data: [] });

        render(
            <MemoryRouter>
                <DonationsPage />
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(screen.getByText(/no se han recibido donaciones/i)).toBeInTheDocument();
        });
    });

    it('muestra tabla con donaciones cuando hay datos', async () => {
        const donations = [
            {
                id: 1,
                amount: 50,
                date: '2024-05-10',
                adoptant: {
                    adoptantName: 'Juan Pérez',
                    email: 'juan@example.com'
                }
            }
        ];

        axios.get.mockResolvedValue({ data: donations });

        render(
            <MemoryRouter>
                <DonationsPage />
            </MemoryRouter>
        );

        await waitFor(() => {
            expect(screen.getByText('Juan Pérez')).toBeInTheDocument();
            expect(screen.getByText('juan@example.com')).toBeInTheDocument();
            expect(screen.getByText('$50.00')).toBeInTheDocument();
            expect(screen.getByText('2024-05-10')).toBeInTheDocument();
        });
    });
});
