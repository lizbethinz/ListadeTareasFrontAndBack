import React, { createContext, useContext, useState } from 'react';
import Snackbar from '@mui/material/Snackbar';
import Alert from '@mui/material/Alert';


const AlertContext = createContext();

export const useAlert = () => {
  return useContext(AlertContext);
};

export const AlertProvider = ({ children }) => {
  const [alertState, setAlertState] = useState({
    open: false,
    message: '',
    severity: 'success',
  });


  const showAlert = (message, severity = 'success') => {
    setAlertState({
      open: true,
      message: message,
      severity: severity,
    });
  };

  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    setAlertState({ ...alertState, open: false });
  };

  const alertComponent = (
    <Snackbar 
      open={alertState.open}
      autoHideDuration={10000}
      onClose={handleClose}
      anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
    >
      <Alert 
        onClose={handleClose} 
        severity={alertState.severity} 
        variant="filled" 
        sx={{ width: '100%' }}
      >
        {alertState.message}
      </Alert>
    </Snackbar>
  );

  return (
   
    <AlertContext.Provider value={{ showAlert }}>
      {children}
      {alertComponent} 
    </AlertContext.Provider>
  );
};