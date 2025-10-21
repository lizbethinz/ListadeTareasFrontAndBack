import React, { useState } from 'react';
import axios from 'axios';
import { Box, Button, TextField, MenuItem, Select, FormControl, InputLabel, Typography } from '@mui/material';
import Menu from '../menu/Menu';
import { useAlert } from "../AlertContext";
import { FaFileExcel } from 'react-icons/fa';
import Container from '@mui/material/Container';




const Reportes = () => { 
    const userId = localStorage.getItem('userId');


    const [filterEstado, setFilterEstado] = useState('TODAS'); 
    const [filterFechaInicio, setFilterFechaInicio] = useState('');
    const [filterFechaFin, setFilterFechaFin] = useState('');
    const { showAlert } = useAlert();


    const handleDownloadReport = async () => {
        const token = localStorage.getItem('token');

        console.log("Token JWT a enviar:", token ? "Token encontrado" : "TOKEN NO ENCONTRADO");
        console.log("ID de Usuario a enviar:", userId);

        if (!token) {
            showAlert('Sesión expirada o no iniciada. Por favor, vuelve a ingresar.', 'warning');
            return;
        }
        if (!userId) {
            showAlert('Error: No se encontró el ID del usuario. Vuelve a iniciar sesión.', 'error');
            return;
        }

        showAlert('Generando reporte Excel...', 'info');

        const params = {};
        
        if (filterEstado && filterEstado !== 'TODAS') {
            params.estado = filterEstado;
        }
        
        if (filterFechaInicio) {
            params.fechaInicio = filterFechaInicio;
        }
        
        if (filterFechaFin) {
            params.fechaFin = filterFechaFin;
        }

        const url = `http://localhost:8080/v1/reportes/excel/${userId}`;

        try {
            const response = await axios.get(
                url, 
                {
                    headers: {
                        'Authorization': `Bearer ${token}` 
                    },
                    params: params, 
                    responseType: 'blob'
                }
            );

            const contentDisposition = response.headers['content-disposition'];
            let filename = `reporte_tareas_${userId}.xlsx`;
            if (contentDisposition) {
                 const filenameMatch = contentDisposition.match(/filename="(.+)"/i);
                 if (filenameMatch && filenameMatch.length === 2) {
                     filename = filenameMatch[1];
                 }
            }

            const blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
            
            const urlBlob = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = urlBlob;
            a.download = filename;
            document.body.appendChild(a);
            a.click();
            
            a.remove();
            window.URL.revokeObjectURL(urlBlob);
            
            showAlert('Reporte Excel descargado exitosamente.', 'success');

        } catch (err) {
                console.error('Error al descargar el reporte:', err);
                if (err.response) {

                if (err.response.status === 401) {
                    showAlert('401: Acceso Denegado. Token inválido o caducado.', 'error');
                } else if (err.response.status === 500) {
                    showAlert('500: Error interno del servidor al generar el reporte.', 'error');
                } else {
                    showAlert(`Error ${err.response.status}: Error al generar el reporte.`, 'error');
                }
            } else {
                showAlert('Error de red o servidor no responde.', 'error');
            }
        }
    };

      return (
        <div>
        <Menu/>
            <Container className='container' maxWidth="sm" sx={{ mt: 5 }}>
                        <Box sx={{ p: 3 }}>
                            
                            <Typography variant="h5" gutterBottom>
                                Selecciona los filtros que necesites aplicar en el reporte
                            </Typography>

                            {userId ? (
                                <>
                                    <Box sx={{ display: 'flex', gap: 2, mb: 3, p: 2, border: '1px solid #ddd', borderRadius: 1 }}>
                                        

                                        <FormControl sx={{ minWidth: 150 }}>
                                            <InputLabel id="estado-label">Estado</InputLabel>
                                            <Select
                                                labelId="estado-label"
                                                value={filterEstado}
                                                label="Estado"
                                                onChange={(e) => setFilterEstado(e.target.value)}
                                            >
                                                <MenuItem value="TODAS">Todas</MenuItem>
                                                <MenuItem value="ACTIVA">Activas</MenuItem>
                                                <MenuItem value="COMPLETADA">Completadas</MenuItem>
                                                <MenuItem value="CANCELADA">Canceladas</MenuItem>
                                            </Select>
                                        </FormControl>

                                        <TextField
                                            label="Fecha Inicio"
                                            type="date"
                                            value={filterFechaInicio}
                                            onChange={(e) => setFilterFechaInicio(e.target.value)}
                                            InputLabelProps={{ shrink: true }}
                                            sx={{ width: 180 }}
                                        />

                                        <TextField
                                            label="Fecha Fin"
                                            type="date"
                                            value={filterFechaFin}
                                            onChange={(e) => setFilterFechaFin(e.target.value)}
                                            InputLabelProps={{ shrink: true }}
                                            sx={{ width: 180 }}
                                        />
                                    </Box>

                                    <Button 
                                        variant="contained" 
                                        onClick={handleDownloadReport} 
                                        sx={{ 
                                            backgroundColor: '#1a5d2c',
                                            '&:hover': { backgroundColor: '#134520' }
                                        }}
                                    ><FaFileExcel style={{ marginRight: 5, marginLeft:5 }} />
                                        Generar Reporte
                                    </Button>
                                </>
                            ) : (
                                <Typography color="error">
                                    No se puede cargar la funcionalidad de reportes. ID de usuario no encontrado.
                                </Typography>
                            )}
                        </Box>
            </Container>

        </div>
    );
}

export default Reportes;