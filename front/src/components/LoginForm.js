import React, { useState } from 'react';

const LoginForm = ({ onSubmit }) => {
    const [form, setForm] = useState({ username: '', password: '' });

    const handleChange = (e) => {
        setForm((prev) => ({ ...prev, [e.target.name]: e.target.value }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        onSubmit(form);
    };

    return (
        <form onSubmit={handleSubmit} className="w-100" style={{ maxWidth: '400px', margin: '0 auto' }}>
            <div className="mb-3">
                <label htmlFor="username" className="form-label">Usuario</label>
                <input
                    type="text"
                    className="form-control"
                    id="username"
                    name="username"
                    value={form.username}
                    onChange={handleChange}
                    required
                />
            </div>
            <div className="mb-3">
                <label htmlFor="password" className="form-label">Contraseña</label>
                <input
                    type="password"
                    className="form-control"
                    id="password"
                    name="password"
                    value={form.password}
                    onChange={handleChange}
                    required
                />
            </div>
            <div className="d-grid">
                <button type="submit" className="btn btn-primary">Aceptar</button>
            </div>
        </form>
    );
};

export default LoginForm;
