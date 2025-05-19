import React from 'react';
import Header from '../components/Header';
import Footer from '../components/Footer';

export default function HomePage() {
    return (
        <div className="d-flex flex-column min-vh-100">
            <Header />

            <main className="flex-grow-1 py-5 bg-white">
                <div className="container">
                    <div className="row justify-content-center">
                        <div className="col-md-10">
                            <h2 className="mb-4">Bienvenido a Adóptame</h2>
                            <p>
                                Adóptame es una plataforma diseñada para conectar refugios de animales con personas interesadas en adoptar una mascota.
                                Nuestro objetivo es facilitar el proceso de adopción responsable y ofrecer una herramienta eficaz tanto para adoptantes como para refugios.
                            </p>
                            <h3 className="mt-4">¿Qué puedes hacer aquí?</h3>
                            <ul className="list-group list-group-flush">
                                <li className="list-group-item">Explorar mascotas disponibles para adopción.</li>
                                <li className="list-group-item">Crear una cuenta y enviar solicitudes de adopción.</li>
                                <li className="list-group-item">Dar seguimiento a tus solicitudes.</li>
                                <li className="list-group-item">Como refugio, puedes registrar mascotas, gestionar solicitudes y contactar adoptantes.</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </main>

            <Footer />
        </div>
    );
}
