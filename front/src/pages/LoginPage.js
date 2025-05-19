import React, { useState, useRef, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import apiClient from '../api/apiClient';
import LoginForm from '../components/LoginForm';
import Header from '../components/Header';
import Footer from '../components/Footer';
import { Modal } from 'bootstrap';

const LoginPage = () => {
    const navigate = useNavigate();
    const [modalMessage, setModalMessage] = useState('');
    const [modalTitle, setModalTitle] = useState('');
    const modalRef = useRef(null);
    const bsModal = useRef(null);

    useEffect(() => {
        if (modalRef.current) {
            bsModal.current = new Modal(modalRef.current, { backdrop: 'static' });
        }
    }, []);


    const showModal = (title, message) => {
        setModalTitle(title);
        setModalMessage(message);
        if (bsModal.current) {
            bsModal.current.show();
        }
    };

    const handleLogin = async (credentials) => {
        try {
            const response = await apiClient.post('/auth/iniciosesion', credentials);
            localStorage.setItem('token', response.data.token);
            localStorage.setItem('userType', response.data.userType);
            showModal('Éxito', 'Inicio de sesión exitoso');
            // Redirigir al cerrar el modal o después de un tiempo
        } catch (err) {
            const msg =
                err.response?.data
                    ? 'Error: ' + JSON.stringify(err.response.data)
                    : err.request
                        ? 'Credenciales incorrectas.'
                        : 'Error de configuración: ' + err.message;
            showModal('Error', msg);
        }
    };

    const handleModalClose = () => {
        bsModal.current.hide();
        if (modalTitle === 'Éxito') {
            navigate('/');
        }
    };

    return (
        <>
            <Header />
            <div className="container mt-5">
                <h2 className="mb-4">Iniciar sesión</h2>
                <LoginForm onSubmit={handleLogin} />
            </div>

            {/* Modal */}
            <div
                className="modal fade"
                id="loginModal"
                tabIndex="-1"
                aria-labelledby="loginModalLabel"
                aria-hidden="true"
                ref={modalRef}
                onClick={(e) => e.stopPropagation()}
            >
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="loginModalLabel">{modalTitle}</h5>
                            <button type="button" className="btn-close" onClick={handleModalClose} aria-label="Cerrar"></button>
                        </div>
                        <div className="modal-body">{modalMessage}</div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" onClick={handleModalClose}>
                                Cerrar
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <Footer />
        </>
    );
};

export default LoginPage;
