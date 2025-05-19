import React, { useState, useRef, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import Header from '../components/Header';
import Footer from '../components/Footer';
import { Modal } from 'bootstrap';

const RegisterPage = () => {
    const navigate = useNavigate();
    const [form, setForm] = useState({
        username: '',
        email: '',
        password: '',
        phoneNumber: '',
        address: '',
        type: 'ADOPTANTE'
    });

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

    const handleModalClose = () => {
        if (bsModal.current) {
            bsModal.current.hide();
        }
        if (modalTitle === 'Éxito') {
            navigate('/iniciosesion');
        }
    };

    const handleChange = e => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = async e => {
        e.preventDefault();
        try {
            await axios.post('/auth/registro', form);
            showModal('Éxito', 'Registro exitoso. Ahora puedes iniciar sesión.');
        } catch (err) {
            console.error('Error: ' + JSON.stringify(err.response?.data));
            let msg;
            if (err.response?.status === 400) {
                msg = `
                    Datos inválidos. Revisa que:
                    <ul>
                        <li>El nombre de usuario tenga entre 4 y 20 caracteres.</li>
                        <li>La contraseña tenga mínimo 8 caracteres.</li>
                        <li>El correo y teléfono tengan un formato válido.</li>
                        <li>La dirección tenga máximo 60 caracteres.</li>
                    </ul>
                `;
            } else if (err.request) {
                msg = 'No hubo respuesta del servidor.';
            } else {
                msg = 'Error de configuración: ' + err.message;
            }
            showModal('Error', msg);
        }
    };

    return (
        <>
            <Header />
            <div className="container mt-5">
                <h2 className="mb-4">Registrarse</h2>
                <form onSubmit={handleSubmit} className="row g-3">
                    <div className="col-md-6">
                        <input className="form-control" type="text" name="username" placeholder="Nombre de usuario (solo letras, números o guion bajo)" onChange={handleChange} required />
                    </div>
                    <div className="col-md-6">
                        <input className="form-control" type="password" name="password" placeholder="Contraseña (mínimo ocho caracteres)" onChange={handleChange} required />
                    </div>
                    <div className="col-md-6">
                        <input className="form-control" type="email" name="email" placeholder="Correo electrónico" onChange={handleChange} required />
                    </div>
                    <div className="col-md-6">
                        <input className="form-control" type="tel" name="phoneNumber" placeholder="Número de teléfono (incluye código de país)" onChange={handleChange} required />
                    </div>
                    <div className="col-12">
                        <input className="form-control" type="text" name="address" placeholder="Dirección" onChange={handleChange} required />
                    </div>
                    <div className="col-12">
                        <select className="form-select" name="type" onChange={handleChange}>
                            <option value="ADOPTANTE">Adoptante</option>
                            <option value="REFUGIO">Refugio</option>
                        </select>
                    </div>
                    <div className="col-12">
                        <button className="btn btn-primary" type="submit">Enviar</button>
                    </div>
                </form>
            </div>

            {/* Modal */}
            <div
                className="modal fade"
                id="registerModal"
                tabIndex="-1"
                aria-labelledby="registerModalLabel"
                aria-hidden="true"
                ref={modalRef}
                onClick={(e) => e.stopPropagation()}
            >
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h5 className="modal-title" id="registerModalLabel">{modalTitle}</h5>
                            <button type="button" className="btn-close" onClick={handleModalClose} aria-label="Cerrar"></button>
                        </div>
                        <div className="modal-body" dangerouslySetInnerHTML={{ __html: modalMessage }} />
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

export default RegisterPage;
