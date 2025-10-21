import Email_img from './assets/Img/Email.png';
import Password_img from './assets/Img/Password.png';
import User_img from './assets/Img/User.png';
import React, { useEffect, useState } from "react";
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

import TextField from '@mui/material/TextField'; 
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import Typography from '@mui/material/Typography'; 
import { FaTasks } from 'react-icons/fa';
import { BsEnvelopeAt } from 'react-icons/bs';
import InputAdornment from '@mui/material/InputAdornment';
import { TbLockPassword } from 'react-icons/tb';
import { FaRegUser } from 'react-icons/fa';
import { useAlert } from './AlertContext';

import './LoginRegistro.css'

const LoginRegistro = () => {
    const[action,setAction]=useState("Iniciar Sesión");


    const[email, setEmail]=useState("");
    const[password, setPassword]=useState("");
    const[nombre, setNombre]=useState("");

    const navigate = useNavigate();

    const [errors, setErrors] = useState({});
    const { showAlert } = useAlert();

    const validate = () => {
        const newErrors = {};
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        //validar el correo
        if (!email) {
            newErrors.email = 'El correo es obligatorio.';
        } else if (!emailRegex.test(email)) {
            newErrors.email = 'El formato del correo no es válido.';
        }

        //validar la contraseña
        if (!password) {
            newErrors.password = 'La contraseña es obligatoria.';
        } else if (password.length < 6) {
            newErrors.password = 'La contraseña debe tener al menos 6 caracteres.';
        }
        if (action === "Registrarse" && !nombre) {
        newErrors.nombre = 'El nombre es obligatorio.';
        }

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleSubmit=()=>{
        if (!validate()) {
            console.log('Validación fallida, no se envía el formulario.');
            return;
        }
        if(action==="Iniciar Sesión"){
            console.log("Iniciar Sesión con:", email, password);
            const loginData = {
                email: email,
                password: password
            };
            console.log("Datos de inicio de sesión:", loginData);
            axios.post('http://localhost:8080/v1/login', loginData).then(response => {
                localStorage.setItem('token', response.data.jwt);
                localStorage.setItem('userId', response.data.userId);
                localStorage.setItem('username', response.data.username);
                localStorage.setItem('nombre', response.data.nombre);
                console.log("Respuesta del servidor:", response.data);
                showAlert(`Inicio de sesión exitoso.`, 'success');
                navigate('/tareas');
            }).catch(error => {
                console.error("Error al iniciar sesión:", error);
                showAlert('Error al iniciar sesión:'+ error.response.data, 'error');
            
            });
        }
        if(action==="Registrarse"){
              const registrodata = {
                nombre: nombre,
                email: email,
                password: password,
                rol: "USER"
            };
            console.log("Registrarse con:", nombre, email, password);     
                axios.post('http://localhost:8080/v1/register', registrodata).then(response => {
                console.log("Respuesta del servidor:", response.data);
                showAlert(`Se ha registrado exitosamente, por favor inicie sesión.`, 'success');
                setAction("Iniciar Sesión");
                setEmail("");
                setPassword("");
                setNombre(""); 
                setErrors({});
                //window.location.href = window.location.href;
            }).catch(error => {
                console.error("Error al registrarse:", error);
                showAlert('Error al registrarse: ' + error.response.data, 'error');
            
            });  
        }if (action === "Recuperar Contraseña") {
            const recoveryData = {
                email: email
            };
            console.log("Recuperar contraseña para:", email);

            axios.post('http://localhost:8080/v1/forgot-password', recoveryData)
            .then(response => {
                showAlert('Se ha enviado un correo electrónico con instrucciones para restablecer tu contraseña.', 'success');
                setAction("Iniciar Sesión"); 
                setEmail("");
                setErrors({});
            }).catch(error => {
                console.error("Error al solicitar recuperación:", error);
                const errorMessage = error.response?.data || 'Error al procesar la solicitud.';
                showAlert('Error: ' + errorMessage, 'error');
            });
            return;
        }
    }

    
  return (
    <Container className='container' maxWidth="sm" sx={{ mt: 5 }}>
        <div className='header'>
        
        <Typography 
            variant="h4" 
            component="h1" 
            className='text'
            sx={{
                color: '#b86614',
                fontSize: '48px',
                fontWeight: 700,
                minWidth: '380px', 
                textAlign: 'center' 
            }}
        >
            {action}
        </Typography>
        
        <div className='underline'></div>

            <form onSubmit={(e) => { e.preventDefault(); handleSubmit(); }}> 
                    <div className='inputs'>
                        {action === "Registrarse" && (
                            <TextField
                                label="Nombre"
                                name="nombre"
                                type="text"
                                fullWidth
                                margin="normal"
                                value={nombre}
                                onChange={(e) => setNombre(e.target.value)}
                                error={!!errors.nombre}
                                helperText={errors.nombre}
                                InputProps={{
                                startAdornment: (
                                    <InputAdornment position="start">
                                        <FaRegUser style={{ color: '#b86614' }} /> 
                                    </InputAdornment>
                                ),
                                }}
                            />
                        )}
                        <TextField
                            label="Email"
                            name="email"
                            type="email" 
                            fullWidth
                            margin="normal"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            error={!!errors.email}
                            helperText={errors.email}
                            InputProps={{
                                startAdornment: (
                                    <InputAdornment position="start">
                                        <BsEnvelopeAt style={{ color: '#b86614' }} /> 
                                    </InputAdornment>
                                ),
                            }}
                        />
                        {(action === "Iniciar Sesión" || action === "Registrarse") && (
                          <TextField
                                label="Contraseña"
                                name="password"
                                type="password"
                                fullWidth
                                margin="normal"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                error={!!errors.password}
                                helperText={errors.password}
                                InputProps={{
                                startAdornment: (
                                <InputAdornment position="start">
                                <TbLockPassword style={{ color: '#b86614' }} /> 
                                </InputAdornment>
                                ),
                                }}
                            />
                    )}
                    </div>
                    {action === "Recuperar Contraseña" && (
                        <div className='Forgot-link'>
                            ¿Recordaste tu contraseña?<span onClick={() => setAction("Iniciar Sesión")}> Volver a Iniciar Sesión</span>
                        </div>
                    )}
                   {/* {action==="Iniciar Sesión"?<div className='Forgot-link'>¿Olvidaste tu contraseña?<span onClick={()=>{setAction("Recuperar Contraseña")}}>Click aquí</span></div>
                    :<div></div>
                    } */}
                    {action==="Registrarse"?<div></div>:
                    <div className='Forgot-link'>¿Aún no tienes cuenta?<span onClick={()=>{setAction("Registrarse")}}>Registrarse</span></div>
                    }
                    {action==="Registrarse"?<div className='Forgot-link'>¿Ya tienes cuenta?<span onClick={()=>{setAction("Iniciar Sesión")}}>Iniciar Sesión</span></div>
                    :<div></div>
                    }
                    <div className='Submit-container'>
                        <div className="boton">
                            <div className="Submit" onClick={handleSubmit}>{action}</div>
                        </div>                         
                    </div>    
            </form>       

          
        </div>
    </Container>
  )
}
export default LoginRegistro                    