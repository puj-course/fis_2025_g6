import React, { useEffect, useState } from 'react';
import Header from '../components/Header';
import Footer from '../components/Footer';
import { useAuth } from '../context/AuthContext';

export default function ProfilePage() {
    const { userType } = useAuth();
    const [user, setUser] = useState(null);
    const [error, setError] = useState('');
    const [isEditing, setIsEditing] = useState(false);
    const [formData, setFormData] = useState({});
    const [validationErrors, setValidationErrors] = useState({});

    useEffect(() => {
        const fetchProfile = async () => {
            try {
                const token = localStorage.getItem('token');
                if (!token || !userType) {
                    setError('No autenticado');
                    return;
                }

                let url = userType === 'ADOPTANT'
                    ? 'http://localhost:8080/adoptantes/me'
                    : 'http://localhost:8080/refugios/me';

                const response = await fetch(url, {
                    headers: { Authorization: `Bearer ${token}` },
                });

                if (!response.ok) throw new Error('Error al obtener el perfil');

                const data = await response.json();
                setUser(data);
                setFormData(data); // Inicializar el formulario con los datos actuales
            } catch (err) {
                setError(err.message);
            }
        };

        fetchProfile();
    }, [userType]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleUpdate = async (e) => {
        e.preventDefault();
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        const newValidationErrors = {};

        const phoneRegex = /^\+?[\d\s\-()]{7,20}$/;

        if (formData.email && !emailRegex.test(formData.email)) {
            newValidationErrors.email = 'Correo electrónico no válido.';
        }
        if (formData.phoneNumber && !phoneRegex.test(formData.phoneNumber)) {
            newValidationErrors.phoneNumber = 'Número no válido. Usa solo dígitos, espacios, paréntesis o guiones (7-20 caracteres).';
        }

        if (formData.address && formData.address.length > 60) {
            newValidationErrors.address = 'Máximo 60 caracteres.';
        }

        if (userType === 'ADOPTANT' && formData.adoptantName && (formData.adoptantName.length > 30 || formData.adoptantName.length < 4)) {
            newValidationErrors.adoptantName = 'El nombre debe tener entre 4 y 30 caracteres.';
        }

        if (userType === 'REFUGE') {
            if (formData.refugeName && (formData.refugeName.length > 30 || formData.refugeName.length < 4)) {
                newValidationErrors.refugeName = 'El nombre debe tener entre 4 y 30 caracteres.';
            }
        }

        if (Object.keys(newValidationErrors).length > 0) {
            setValidationErrors(newValidationErrors);
            return;
        }

        setValidationErrors({});

        try {
            const token = localStorage.getItem('token');
            if (!token || !userType) {
                setError('No autenticado');
                return;
            }

            let url = userType === 'ADOPTANT'
                ? 'http://localhost:8080/adoptantes/me'
                : 'http://localhost:8080/refugios/me';

            // Filtrar campos que han sido modificados
            const updatedFields = {};
            for (const key in formData) {
                if (formData[key] !== user[key] && formData[key] !== undefined && formData[key] !== '') {
                    updatedFields[key] = formData[key];
                }
            }

            if (Object.keys(updatedFields).length === 0) {
                setIsEditing(false);
                return;
            }

            const response = await fetch(url, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${token}`,
                },
                body: JSON.stringify(updatedFields),
            });

            if (!response.ok) throw new Error('Error al actualizar perfil');

            const updatedUser = await response.json();
            setUser(updatedUser);
            setFormData(updatedUser);
            setIsEditing(false);
        } catch (err) {
            setError(err.message);
        }
    };

    if (error) {
        return <div className="alert alert-danger">Error: {error}</div>;
    }

    if (!user) {
        return <div className="text-center mt-5">Cargando perfil...</div>;
    }

    return (
        <>
            <Header />
            <div className="container mt-5">
                <h2>Mi perfil</h2>
                <div className="card mt-4">
                    <div className="card-body">
                        {isEditing ? (
                            <form onSubmit={handleUpdate}>
                                <div className="mb-3">
                                    <label htmlFor='email' className="form-label">Correo electrónico</label>
                                    <input
                                        id='email'
                                        type="email"
                                        className={`form-control ${validationErrors.email ? 'is-invalid' : ''}`}
                                        name="email"
                                        value={formData.email || ''}
                                        onChange={handleChange}
                                    />
                                    {validationErrors.email && <div className="invalid-feedback">{validationErrors.email}</div>}
                                </div>
                                <div className="mb-3">
                                    <label htmlFor='phoneNumber' className="form-label">Teléfono</label>
                                    <input
                                        id='phoneNumber'
                                        className={`form-control ${validationErrors.phoneNumber ? 'is-invalid' : ''}`}
                                        name="phoneNumber"
                                        value={formData.phoneNumber || ''}
                                        onChange={handleChange}
                                    />
                                    {validationErrors.phoneNumber && <div className="invalid-feedback">{validationErrors.phoneNumber}</div>}
                                </div>

                                <div className="mb-3">
                                    <label htmlFor='address' className="form-label">Dirección</label>
                                    <input
                                        id='address'
                                        className={`form-control ${validationErrors.address ? 'is-invalid' : ''}`}
                                        name="address"
                                        value={formData.address || ''}
                                        onChange={handleChange}
                                    />
                                    {validationErrors.address && <div className="invalid-feedback">{validationErrors.address}</div>}
                                </div>

                                {userType === 'ADOPTANT' && (
                                    <div className="mb-3">
                                        <label htmlFor='adoptantName' className="form-label">Nombre del adoptante</label>
                                        <input
                                            id='adoptantName'
                                            className={`form-control ${validationErrors.adoptantName ? 'is-invalid' : ''}`}
                                            name="adoptantName"
                                            value={formData.adoptantName || ''}
                                            onChange={handleChange}
                                        />
                                        {validationErrors.adoptantName && <div className="invalid-feedback">{validationErrors.adoptantName}</div>}
                                    </div>
                                )}

                                {userType === 'REFUGE' && (
                                    <>
                                        <div className="mb-3">
                                            <label htmlFor='refugeName' className="form-label">Nombre del refugio</label>
                                            <input
                                                id='refugeName'
                                                className={`form-control ${validationErrors.refugeName ? 'is-invalid' : ''}`}
                                                name="refugeName"
                                                value={formData.refugeName || ''}
                                                onChange={handleChange}
                                            />
                                            {validationErrors.refugeName && <div className="invalid-feedback">{validationErrors.refugeName}</div>}
                                        </div>
                                        <div className="mb-3">
                                            <label htmlFor='description' className="form-label">Descripción</label>
                                            <textarea
                                                id='description'
                                                className="form-control"
                                                name="description"
                                                rows="3"
                                                value={formData.description || ''}
                                                onChange={handleChange}
                                            />
                                        </div>
                                    </>
                                )}
                                <button type="submit" className="btn btn-primary me-2">Guardar</button>
                                <button type="button" className="btn btn-secondary" onClick={() => setIsEditing(false)}>Cancelar</button>
                            </form>
                        ) : (
                            <>
                                <h5 className="card-title">{user.username}</h5>
                                {userType === 'ADOPTANT' && (
                                    <p className="card-text"><strong>Nombre del adoptante:</strong> {user.adoptantName}</p>
                                )}
                                {userType === 'REFUGE' && (
                                    <>
                                        <p className="card-text"><strong>Nombre del refugio:</strong> {user.refugeName}</p>
                                        <p className="card-text"><strong>Descripción:</strong> {user.description}</p>
                                    </>
                                )}
                                <p className="card-text"><strong>Correo electrónico:</strong> {user.email}</p>
                                <p className="card-text"><strong>Teléfono:</strong> {user.phoneNumber}</p>
                                <p className="card-text"><strong>Dirección:</strong> {user.address}</p>
                                <button className="btn btn-outline-primary mt-3" onClick={() => setIsEditing(true)}>Editar perfil</button>
                            </>
                        )}
                    </div>
                </div>
            </div>
            <Footer />
        </>
    );
}