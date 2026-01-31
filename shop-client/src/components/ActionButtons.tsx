import { Delete, Edit } from '@mui/icons-material';
import { Box, IconButton, Tooltip } from '@mui/material';

type Props = {
    handleDelete: () => void;
    handleEdit: () => void;
};

const ActionButtons = ({ handleDelete, handleEdit }: Props) => {
    return (
        <Box
            sx={{
                position: 'absolute',
                top: { xs: 8, sm: 12, md: 16 },
                right: { xs: 8, sm: 12, md: 16 },
                display: 'flex',
                gap: { xs: 0.5, sm: 1 },
                zIndex: 10,
            }}
        >
            <Tooltip title="Modifier">
                <IconButton
                    color="primary"
                    onClick={handleEdit}
                    sx={{
                        padding: { xs: '6px', sm: '8px' },
                        '&:hover': {
                            backgroundColor: 'rgba(25, 118, 210, 0.08)',
                        },
                    }}
                >
                    <Edit sx={{ fontSize: { xs: '1.2rem', sm: '1.5rem' } }} />
                </IconButton>
            </Tooltip>
            <Tooltip title="Supprimer">
                <IconButton
                    color="error"
                    onClick={handleDelete}
                    sx={{
                        padding: { xs: '6px', sm: '8px' },
                        '&:hover': {
                            backgroundColor: 'rgba(211, 47, 47, 0.08)',
                        },
                    }}
                >
                    <Delete sx={{ fontSize: { xs: '1.2rem', sm: '1.5rem' } }} />
                </IconButton>
            </Tooltip>
        </Box>
    );
};

export default ActionButtons;
