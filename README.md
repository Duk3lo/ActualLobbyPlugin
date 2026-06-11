# ActualLobbyPlugin

Plugin de lobby para **Folia**.

## Características

- Comando principal de administración.
- Configuración del spawn del lobby.
- Recarga de configuración.
- Soporte para Folia.

## Comando principal

### `/lobby`
Comando principal del plugin.

#### Subcomandos
- `/lobby reload`  
  Recarga la configuración del plugin.

- `/lobby setspawn [x y z yaw pitch]`  
  Guarda la posición actual del jugador como spawn.  
  También permite pasar coordenadas relativas o absolutas.

## Permisos

- `lobbyplugin.admin`  
  Permite usar los subcomandos de administración.

## Instalación

1. Descarga el `.jar`.
2. Colócalo en la carpeta `plugins/`.
3. Inicia el servidor Folia.
4. Configura el plugin si es necesario.

## Soporte

Compatible con **Folia**.