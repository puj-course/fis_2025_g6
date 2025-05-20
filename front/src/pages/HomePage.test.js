import React from 'react';
import { render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import HomePage from '../pages/HomePage';

describe('HomePage', () => {
  test('muestra el título principal y la descripción', () => {
    render(
      <MemoryRouter>
        <HomePage />
      </MemoryRouter>
    );

    expect(screen.getByRole('heading', { level: 2, name: /bienvenido a adóptame/i })).toBeInTheDocument();
    expect(screen.getByText(/adóptame es una plataforma diseñada para conectar refugios de animales/i)).toBeInTheDocument();
    expect(screen.getByRole('heading', { level: 3, name: /qué puedes hacer aquí\?/i })).toBeInTheDocument();
  });

  test('muestra la lista de funcionalidades', () => {
    render(
      <MemoryRouter>
        <HomePage />
      </MemoryRouter>
    );

    const items = [
      /explorar mascotas disponibles para adopción/i,
      /crear una cuenta y enviar solicitudes de adopción/i,
      /dar seguimiento a tus solicitudes/i,
      /como refugio, puedes registrar mascotas, gestionar solicitudes y contactar adoptantes/i,
    ];

    items.forEach((text) => {
      expect(screen.getByText(text)).toBeInTheDocument();
    });
  });
});
