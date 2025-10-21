import * as React from 'react';
import PropTypes from 'prop-types';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import CssBaseline from '@mui/material/CssBaseline';
import Divider from '@mui/material/Divider';
import Drawer from '@mui/material/Drawer';
import IconButton from '@mui/material/IconButton';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemText from '@mui/material/ListItemText';
import MenuIcon from '@mui/icons-material/Menu';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import { useNavigate } from 'react-router-dom';
import { FaTasks } from 'react-icons/fa';


const drawerWidth = 240;
const navItems = ['Nueva tarea', 'Cerrar Sesión'];
const nombre =  localStorage.getItem('nombre');

function Menu(props) {
  const { window } = props;
  const [mobileOpen, setMobileOpen] = React.useState(false);

  const navigate = useNavigate();


  const handleDrawerToggle = () => {
    setMobileOpen((prevState) => !prevState);
  };

  const handleLogout = () => {
    localStorage.clear(); 
    navigate('/'); 
    setMobileOpen(false); 
  };

  /*const nuevaTarea = ()=>{
    console.log("nueva tarea");
    navigate('/nuevaTarea'); 
    setMobileOpen(false); 
  };*/

  const listadoTareas= ()=>{
    console.log("tareas activas");
    navigate('/tareas'); 
    setMobileOpen(false); 
  };

const listadoTareasCompletadas = ()=>{
    console.log("tareas completadas");
    //navigate('/TareasCompletadas'); 
    setMobileOpen(false); 
  };

  const drawer = (
    <Box onClick={handleDrawerToggle} sx={{ textAlign: 'center' }}>
      <FaTasks style={{ marginRight: 5, marginLeft:5, marginTop:15 }} />
      <Typography variant="h6" sx={{ my: 2 }}>
      </Typography>
      <Divider />
      <List>
        {navItems.map((item) => (
          <ListItem key={item} disablePadding 
          onClick={
            item === 'Cerrar Sesión' ? handleLogout 
          :item === 'Nueva tarea' ? props.onNewTaskClick
          :item ==='Tareas activas'? listadoTareas
          :item ==='Tareas completadas'? listadoTareasCompletadas
          : undefined}>
            <ListItemButton sx={{ textAlign: 'center' }}>
              <ListItemText primary={item} />
            </ListItemButton>
          </ListItem>
        ))}
      </List>
    </Box>
  );

  const container = window !== undefined ? () => window().document.body : undefined;

  return (
    <Box sx={{ display: 'flex' }}>
      <CssBaseline />
      <AppBar component="nav">
        <Toolbar>
          <IconButton
            color="inherit"
            aria-label="open drawer"
            edge="start"
            onClick={handleDrawerToggle}
            sx={{ mr: 2, display: { sm: 'none' } }}
          >
            <MenuIcon />
          </IconButton>
           <FaTasks style={{ marginRight: 5, marginLeft:5, }}/>
          <Typography
            variant="h6"
            component="div"
            sx={{ flexGrow: 1, display: { xs: 'none', sm: 'block' }, textAlign: 'left' }}
          >Organizador de tareas
          </Typography>
          <Box sx={{ display: { xs: 'none', sm: 'block' } }}>
            {navItems.map((item) => (
              <Button key={item} sx={{ color: '#fff' }} 
              onClick={
                item === 'Cerrar Sesión' ? handleLogout 
                :item === 'Nueva tarea' ? props.onNewTaskClick
                :item ==='Tareas activas'? listadoTareas
                :item ==='Tareas completadas'? listadoTareasCompletadas
          : undefined}>
                {item}
              </Button>
            ))}
          </Box>
        </Toolbar>
      </AppBar>
      <nav>
        <Drawer
          container={container}
          variant="temporary"
          open={mobileOpen}
          onClose={handleDrawerToggle}
          ModalProps={{
            keepMounted: true, // Better open performance on mobile.
          }}
          sx={{
            display: { xs: 'block', sm: 'none' },
            '& .MuiDrawer-paper': { boxSizing: 'border-box', width: drawerWidth },
          }}
        >
          {drawer}
        </Drawer>
      </nav>
    </Box>
  );
}

Menu.propTypes = {
  /**
   * Injected by the documentation to work in an iframe.
   * You won't need it on your project.
   */
  window: PropTypes.func,
};

export default Menu;