import React from 'react';
import { render, screen, waitFor, fireEvent } from '@testing-library/react';
import ProfilePage from './ProfilePage';

global.MutationObserver = class {
    constructor(callback) { }
    disconnect() { }
    observe() { }
    takeRecords() { return []; }
};

// Mock de componentes
jest.mock('../components/Header', () => () => <div data-testid="header" />);
jest.mock('../components/Footer', () => () => <div data-testid="footer" />);

// Mock del hook useAuth
jest.mock('../context/AuthContext', () => ({
    useAuth: () => ({
        userType: 'ADOPTANT',
    }),
}));

beforeEach(() => {
    // Mock localStorage
    Storage.prototype.getItem = jest.fn((key) => {
        if (key === 'token') return 'fake-token';
        return null;
    });

    // Mock fetch
    global.fetch = jest.fn(() =>
        Promise.resolve({
            ok: true,
            json: () =>
                Promise.resolve({
                    email: 'john@example.com',
                    phoneNumber: '123456789',
                    address: 'Calle Falsa 123',
                    adoptantName: 'Juan Pérez',
                }),
        })
    );
});

describe('ProfilePage', () => {
    it('muestra los datos del perfil cuando se carga correctamente', async () => {
        render(<ProfilePage />);

        expect(screen.getByText(/Cargando perfil/i)).toBeInTheDocument();

        await waitFor(() => {
            // Verificar etiquetas y textos por separado
            expect(screen.getByText('Nombre del adoptante:')).toBeInTheDocument();
            expect(screen.getByText('Juan Pérez')).toBeInTheDocument();

            expect(screen.getByText('Correo electrónico:')).toBeInTheDocument();
            expect(screen.getByText('john@example.com')).toBeInTheDocument();
        });
    });

    it('permite activar modo edición', async () => {
        render(<ProfilePage />);

        await waitFor(() => {
            expect(screen.getByText('Editar perfil')).toBeInTheDocument();
        });

        fireEvent.click(screen.getByText('Editar perfil'));

        // Usamos getByLabelText (pero ojo: los inputs deben tener id para que esto funcione)
        expect(screen.getByLabelText('Correo electrónico')).toBeInTheDocument();
    });
});
