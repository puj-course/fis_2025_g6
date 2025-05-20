import React from 'react';
import { render, screen } from '@testing-library/react';
import RefugePetsPage from './RefugePetsPage';
import '@testing-library/jest-dom';
import { MemoryRouter } from 'react-router-dom';

test('muestra el título de mascotas', () => {
    render(
        <MemoryRouter>
            <RefugePetsPage />
        </MemoryRouter>
    );
    expect(screen.getByText(/Mis mascotas/i)).toBeInTheDocument();
});
