import React from 'react';
import { Link, useNavigate } from 'react-router-dom';

export default function Header() {
    const navigate = useNavigate();
    const token = localStorage.getItem('token');

    const handleLogout = () => {
        localStorage.removeItem('token');
        navigate('/iniciosesion');
    };

    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-primary">
            <div className="container">
                <Link className="navbar-brand" to="/">Adóptame</Link>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarNav">
                    <ul className="navbar-nav ms-auto">
                        {token ? (
                            <>
                                <li className="nav-item">
                                    <Link className="nav-link" to="/lista-refugios">Lista de refugios</Link>
                                </li>
                                <li className="nav-item">
                                    <Link className="nav-link" to="/lista-mascotas">Lista de mascotas</Link>
                                </li>
                                <li className="nav-item">
                                    <Link className="nav-link" to="/solicitudes">Mis solicitudes</Link>
                                </li>
                                <li className="nav-item">
                                    <Link className="nav-link" to="/perfil">Perfil</Link>
                                </li>
                                <li className="nav-item">
                                    <button className="btn btn-link nav-link" onClick={handleLogout}>
                                        Cerrar sesión
                                    </button>
                                </li>
                            </>
                        ) : (
                            <>
                                <li className="nav-item">
                                    <Link className="nav-link" to="/registro">Registrarse</Link>
                                </li>
                                <li className="nav-item">
                                    <Link className="nav-link" to="/iniciosesion">Iniciar sesión</Link>
                                </li>
                            </>
                        )}
                    </ul>
                </div>
            </div>
        </nav>
    );
}
