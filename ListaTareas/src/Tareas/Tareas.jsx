import React, { useEffect, useState } from "react"; 
import axios from 'axios';
import { DataGrid } from '@mui/x-data-grid';
import Paper from '@mui/material/Paper';
import { FaPencilAlt } from 'react-icons/fa';
import { FaRegTrashAlt } from 'react-icons/fa';
import Button from '@mui/material/Button';
import { FaCheck } from 'react-icons/fa';
import Tooltip from '@mui/material/Tooltip';

import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';
import TextField from '@mui/material/TextField';
import Switch from '@mui/material/Switch';
import FormControlLabel from '@mui/material/FormControlLabel';
import Menu from "../menu/Menu";
import TareaModal from "./TareaModal";
import Swal from 'sweetalert2';
import { useAlert } from "../AlertContext";

const Tareas = () => {
    

    const [tareas, setTareas] = useState([]);
    const [error, setError] = useState(null);
    const [isLoading, setIsLoading] = useState(false);
    const nombre =  localStorage.getItem('nombre');
    const [modalOpen, setModalOpen] = useState(false);
    const [currentTarea, setCurrentTarea] = useState(null);
 
    const { showAlert } = useAlert();

    const TAREA_INICIAL = {
    id: null, 
    nombre: '',
    descripcion: '',
    completada: false,
    cancelada: false,
    fechaObjetivo: ''
    };

    const handleNewTaskClick = () => {
    setCurrentTarea(TAREA_INICIAL); 
    setModalOpen(true);            
    };


    const getTareas = async() => {
        setIsLoading(true); 
        setError(null); // Limpiar errores anteriores
        setTareas([]); // Opcional: limpiar la tabla anterior
        const token = localStorage.getItem('token');
        console.log("Token a enviar:", token);
        const userId = localStorage.getItem('userId');
        console.log("usuario ",userId);

        if (!token || !userId) {
            setError("No se encontró la información de autenticación.");
            setIsLoading(false);
            return;
        }

        try {
            const response = await axios.get(
                `http://localhost:8080/v1/tareas/listado/activas/${userId}`,
                {
                    headers: {
                        'Authorization': `Bearer ${token}` 
                    }
                }
            );
            
            setTareas(response.data);
            setError(null);

        } catch (err) {
            console.log('Error al cargar tareas:', err);
            setError("Error al cargar las tareas.");
        } finally {
            setIsLoading(false);
        }
        
    };
  useEffect(() => {
        getTareas(); 
    }, []);

    if (isLoading) {
        return <div>Cargando tareas...</div>;
    }

    if (isLoading) {
        return <div>Cargando tareas...</div>;
    }

    if (error) {
        return <div style={{ color: 'red' }}>Error: {error}</div>;
    }

const columns = [
  { field: 'id', headerName: 'ID', width: 70, },
  { field: 'nombre', headerName: 'Nombre',flex:1, minWidth:150 },
  { field: 'descripcion', headerName: 'Descripción', flex: 2, minWidth: 170 },
  {
    field: 'completada',
    headerName: 'Estado',
    width: 100,
    headerAlign: 'center', 
    align: 'center',
    valueGetter: (value) => value ? 'Completada' : 'Pendiente',
  },
  { field: 'fechaCreacion', headerName: 'Fecha Creación', width: 130,
    headerAlign: 'center', 
    align: 'center', },
  { field: 'fechaObjetivo', headerName: 'Fecha Objetivo', width: 130,
    headerAlign: 'center', 
    align: 'center',
    valueGetter: (value) => {
      return value ? value : '-'; 
    }
   },
  {
    field: 'acciones',
    headerName: 'Acciones',
    sortable: false,
    filterable: false,
    width: 180,
    headerAlign: 'center', 
    renderCell: (params) => {
      const tareaCompletada = params.row.completada;

      const handleEditar = () => {
        console.log('Editar Tarea ID:', params.row.id);
        setCurrentTarea({
            ...params.row,
            completada: params.row.completada === 'Completada' ? true : params.row.completada
        });
        setModalOpen(true);
      };

      const handleCancelar = async() => {
        console.log('Cancelando tarea:', params.row.id);

        const token = localStorage.getItem('token');

        const tareaACancelar =({
             ...params.row,
             cancelada:true,
             completada: false
        })
        try {
            await axios.put(
                `http://localhost:8080/v1/tareas/actualizatarea`,
                tareaACancelar,
                {
                    headers: {
                        'Authorization': `Bearer ${token}` 
                    }
                }
            );
            showAlert(`Tarea cancelada exitosamente.`, 'success');
            getTareas(); 

        } catch (err) {
            console.error('Error al cancelar la tarea:', err);
            showAlert('Hubo un error al cancelar la tarea.', 'error');
        }
      };
    const handleFinalizar = async() => {
        console.log('Cambiar estado Tarea ID:', params.row.id);
        console.log('Finalizando tarea:', params.row.id);

        const token = localStorage.getItem('token');

        const tareaAFinalizar =({
             ...params.row,
             completada: true
        })
        try {
            await axios.put(
                `http://localhost:8080/v1/tareas/actualizatarea`,
                tareaAFinalizar,
                {
                    headers: {
                        'Authorization': `Bearer ${token}` 
                    }
                }
            );
            showAlert(`Tarea finalizada exitosamente.`, 'success');
            getTareas(); 

        } catch (err) {
            console.error('Error al finalizar la tarea:', err);
            showAlert('Hubo un error al finalizar la tarea.', 'error');
        }
      };

      return (
        <>
        <div style={{ 
            display: 'flex', 
            alignItems: 'center', 
            justifyContent: 'center', 
            width: '100%', 
            height: '100%' 
        }}>
        <Tooltip title="Editar Tarea"> 
            <button 
                onClick={handleEditar} 
                alt="Editar"
                style={{ 
                    marginRight: 8,
                    padding: '5px 10px',
                    backgroundColor: '#3A9AE8',
                    color:'white' }}
            ><FaPencilAlt style={{ marginRight: 5, marginLeft:5 }} />
            </button>
        </Tooltip>
        <Tooltip title="Finalizar Tarea">
            <button 
                onClick={handleFinalizar}
                style={{ 
                marginRight: 8,
                padding: '5px 10px',
                backgroundColor:'#3AE888',
                color: 'white'
                }}
            >
                <FaCheck style={{ marginRight: 5, marginLeft:5 }} />
            </button>
        </Tooltip>            
        <Tooltip title="Eliminar Tarea">
            <button 
                onClick={handleCancelar}
                style={{ 
                padding: '5px 10px',
                backgroundColor:'#E83A43',
                color: 'white'
                }}
            >
                <FaRegTrashAlt style={{ marginRight: 5, marginLeft:5 }} />
            </button>
        </Tooltip>

        </div>

        </>
      );
    },
  },
];

const handleInputChange = (e) => {
    const { name, value, checked, type } = e.target;
    
    setCurrentTarea({
        ...currentTarea,
        [name]: type === 'checkbox' ? checked : value,
    });
};

const handleGuardarCambios = async () => {
    if (!currentTarea) return;

    const nombre = currentTarea.nombre ? currentTarea.nombre.trim() : '';
    const descripcion = currentTarea.descripcion ? currentTarea.descripcion.trim() : '';

    if (!nombre) {
        showAlert(`El nombre de la tarea es obligatorio.`, 'warning');
        return;
    }

    if (nombre.length > 255) {
        showAlert(`El nombre de la tarea no puede exceder los 255 caracteres.`, 'warning');
        return;
    }

    if (!descripcion) {
        showAlert(`La descripción de la tarea es obligatoria.`, 'warning');
        return;
    }

    if (descripcion.length > 255) {
        showAlert(`La descripción de la tarea no puede exceder los 255 caracteres.`, 'warning');
        return;
    }

    const token = localStorage.getItem('token'); 

    const isNewTask = currentTarea.id === null;

   const payload = {
        id: currentTarea.id,
        nombre: currentTarea.nombre,
        descripcion: currentTarea.descripcion,
        completada: currentTarea.completada,
        cancelada: currentTarea.cancelada || false, 
        fechaObjetivo: currentTarea.fechaObjetivo ? currentTarea.fechaObjetivo.split('T')[0] : null 
    };

    if (isNewTask) {
        const userId = localStorage.getItem('userId');
        payload.idUsuario = userId ? JSON.parse(userId) : null;
        if (!payload.idUsuario) {
            showAlert('No se encontró el ID de usuario.', 'error');
            return;
        }
    } else {
        payload.id = currentTarea.id;
    }

    try {
        const url = isNewTask ? 
            `http://localhost:8080/v1/tareas/nuevatarea` : 
            `http://localhost:8080/v1/tareas/actualizatarea`;
            
        const method = isNewTask ? axios.post : axios.put;

        await method(
            url,
            payload, 
            { headers: { 'Authorization': `Bearer ${token}` } }
        );
        
        setModalOpen(false);
        getTareas();         
        showAlert(`Tarea ${isNewTask ? 'registrada' : 'actualizada'} exitosamente.`, 'success');


    } catch (err) {
        showAlert('Hubo un error al guardar los cambios en el servidor.', 'error');
            console.error('Error al actualizar la tarea:', err);
    }
   
};


    return (
        <div>
            <Menu onNewTaskClick={handleNewTaskClick}/>
           <Paper sx={{ height: 400, width: '100%',overflowX: 'auto' }}>
                <DataGrid
                    rows={tareas}
                    columns={columns}
                    pageSizeOptions={[5, 10]}
                    columnVisibilityModel={{
                        id: false,
                    }}
                    localeText={{
                    noRowsLabel: 'Sin tareas activas', 
                    noResultsOverlayLabel: 'No se encontraron resultados.', 
                }}
                    initialState={{
                        pagination: { 
                            paginationModel: { page: 0, pageSize: 5 } 
                        }
                    }}
                    sx={{ border: 0 }}
                />
            </Paper>
            <TareaModal 
            modalOpen={modalOpen}
            setModalOpen={setModalOpen}
            currentTarea={currentTarea}
            handleInputChange={handleInputChange}
            handleGuardarCambios={handleGuardarCambios}
            />
        </div>

        
    );
}
export default Tareas   