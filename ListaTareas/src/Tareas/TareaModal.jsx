import React from 'react'; 
import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';
import TextField from '@mui/material/TextField';
import Switch from '@mui/material/Switch';
import FormControlLabel from '@mui/material/FormControlLabel';
import Button from '@mui/material/Button';


const TareaModal = ({ modalOpen, setModalOpen, currentTarea, handleInputChange, handleGuardarCambios }) => {
    

    if (!currentTarea) return null; 

    const isEditing = currentTarea.id !== null;

    return (
        <Dialog open={modalOpen} onClose={() => setModalOpen(false)} fullWidth maxWidth="sm">
            <DialogTitle>
                {currentTarea.id === null ? 'Registrar Nueva Tarea' : `Editar Tarea`}
            </DialogTitle>
            <DialogContent>
                <TextField
                    autoFocus
                    margin="dense"
                    name="nombre"
                    label="Nombre de Tarea"
                    type="text"
                    fullWidth
                    variant="standard"
                    value={currentTarea.nombre || ''}
                    onChange={handleInputChange}
                    required
                    inputProps={{ maxLength: 255 }}
                    sx={{ mb: 2 }}
                />
                <TextField
                    margin="dense"
                    name="descripcion"
                    label="Descripción"
                    type="text"
                    fullWidth
                    multiline
                    rows={3}
                    variant="standard"
                    value={currentTarea.descripcion || ''}
                    onChange={handleInputChange}
                    required
                    inputProps={{ maxLength: 255 }}
                    sx={{ mb: 2 }}
                />
                {isEditing && (
                    <FormControlLabel
                        control={
                            <Switch
                                checked={currentTarea.completada || false}
                                onChange={handleInputChange}
                                name="completada"
                                color="primary"
                            />
                        }
                        label="Tarea Completada"
                    />
                )}
                <TextField
                    margin="dense"
                    name="fechaObjetivo"
                    label="Fecha Objetivo"
                    type="date" 
                    fullWidth
                    variant="standard"
                    value={currentTarea.fechaObjetivo ? currentTarea.fechaObjetivo.split('T')[0] : ''} 
                    onChange={handleInputChange}
                    InputLabelProps={{ shrink: true }} 
                    sx={{ mb: 2 }}
                />
            </DialogContent>
            <DialogActions>
                <Button onClick={() => setModalOpen(false)} 
                style={{ 
                backgroundColor: '#E83A43',
                color:'white' 
                }} >
                    Cancelar
                </Button>
                <Button onClick={handleGuardarCambios} variant="contained"
                style={{ 
                backgroundColor: '#3A9AE8', 
                }} >
                    Guardar Cambios
                </Button>
            </DialogActions>
        </Dialog>
    );
};
export default TareaModal