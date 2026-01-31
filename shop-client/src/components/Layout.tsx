import React, { useState } from 'react';
import {
    AppBar,
    Box,
    Button,
    Toolbar,
    Typography,
    IconButton,
    Drawer,
    List,
    ListItem,
    ListItemButton,
    ListItemText,
    useMediaQuery,
    useTheme,
} from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import { useNavigate } from 'react-router-dom';
import Loader from './Loader';
import SwitchLanguage from './SwitchLanguage';

type Props = {
    children: React.ReactNode;
};

const navItems = [
    { label: 'Boutiques', path: '/' },
    { label: 'Produits', path: '/product' },
    { label: 'Catégories', path: '/category' },
];

const Layout = ({ children }: Props) => {
    const navigate = useNavigate();
    const theme = useTheme();
    const isMobile = useMediaQuery(theme.breakpoints.down('md'));
    const [mobileOpen, setMobileOpen] = useState(false);

    const handleDrawerToggle = () => {
        setMobileOpen(!mobileOpen);
    };

    const handleNavigation = (path: string) => {
        navigate(path);
        setMobileOpen(false);
    };

    const drawer = (
        <Box onClick={handleDrawerToggle} sx={{ textAlign: 'center' }}>
            <Typography variant="h6" sx={{ my: 2 }}>
                Gestion de boutiques
            </Typography>
            <List>
                {navItems.map((item) => (
                    <ListItem key={item.label} disablePadding>
                        <ListItemButton sx={{ textAlign: 'center' }} onClick={() => handleNavigation(item.path)}>
                            <ListItemText primary={item.label} />
                        </ListItemButton>
                    </ListItem>
                ))}
            </List>
            <Box sx={{ p: 2 }}>
                <SwitchLanguage />
            </Box>
        </Box>
    );

    return (
        <div style={{ margin: 0, padding: 0 }}>
            <AppBar
                component="nav"
                position="fixed"
                sx={{
                    top: 0,
                    left: 0,
                    right: 0,
                    zIndex: theme.zIndex.drawer + 1,
                }}
            >
                <Toolbar className="header">
                    {isMobile && (
                        <IconButton
                            color="inherit"
                            aria-label="open drawer"
                            edge="start"
                            onClick={handleDrawerToggle}
                            sx={{ mr: 2 }}
                        >
                            <MenuIcon />
                        </IconButton>
                    )}

                    <Typography
                        variant="h6"
                        onClick={() => navigate('/')}
                        sx={{ cursor: 'pointer', flexGrow: isMobile ? 1 : 0 }}
                    >
                        Gestion de boutiques
                    </Typography>

                    {!isMobile && (
                        <>
                            <Box sx={{ flexGrow: 1 }} />
                            <Box>
                                {navItems.map((item) => (
                                    <Button key={item.label} sx={{ color: '#fff' }} onClick={() => navigate(item.path)}>
                                        {item.label}
                                    </Button>
                                ))}
                            </Box>
                            <Box>
                                <SwitchLanguage />
                            </Box>
                        </>
                    )}
                </Toolbar>
            </AppBar>

            <Drawer
                variant="temporary"
                open={mobileOpen}
                onClose={handleDrawerToggle}
                ModalProps={{
                    keepMounted: true,
                }}
                sx={{
                    display: { xs: 'block', md: 'none' },
                    '& .MuiDrawer-paper': {
                        boxSizing: 'border-box',
                        width: 240,
                        top: 64, // Ajoutez cette ligne pour que le drawer commence sous le header
                        height: 'calc(100% - 64px)', // Ajustez la hauteur
                    },
                }}
            >
                {drawer}
            </Drawer>

            <Loader />
            <div style={{ margin: 0, padding: 0 }}>{children}</div>
        </div>
    );
};

export default Layout;
