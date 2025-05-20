 <Header />
  
            <div className="container mt-5">
                <h2>Mi perfil</h2>
                <div className="card mt-4">
                    <div className="card-body">
                        {isEditing ? (
                            <form onSubmit={handleUpdate}>
                                <div className="mb-3">
                                    <label className="form-label">Nombre de usuario</label>
                                    <input
                                        className="form-control"
                                        name="username"
                                        value={formData.username || ''}
                                        onChange={handleChange}
                                    />
                                </div>
                                <div className="mb-3">
                                    <label className="form-label">Correo electrónico</label>
                                    <input
                                        className="form-control"
                                        name="email"
                                        value={formData.email || ''}
                                        onChange={handleChange}
                                    />
                                </div>
                                <div className="mb-3">
                                    <label className="form-label">Teléfono</label>
                                    <input
                                        className="form-control"
                                        name="phoneNumber"
                                        value={formData.phoneNumber || ''}
                                        onChange={handleChange}
                                    />
                                </div>
                                <div className="mb-3">
                                    <label className="form-label">Dirección</label>
                                    <input
                                        className="form-control"
                                        name="address"
                                        value={formData.address || ''}
                                        onChange={handleChange}
                                    />
                                </div>
                                {userType === 'ADOPTANT' && (
                                    <div className="mb-3">
                                        <label className="form-label">Nombre del adoptante</label>
                                        <input
                                            className="form-control"
                                            name="adoptantName"
                                            value={formData.adoptantName || ''}
                                            onChange={handleChange}
                                        />
                                    </div>
                                )}
                                {userType === 'REFUGE' && (
                                    <>
                                        <div className="mb-3">
                                            <label className="form-label">Nombre del refugio</label>
                                            <input
                                                className="form-control"
                                                name="refugeName"
                                                value={formData.refugeName || ''}
                                                onChange={handleChange}
                                            />
                                        </div>
                                        <div className="mb-3">
                                            <label className="form-label">Descripción</label>
                                            <textarea
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
