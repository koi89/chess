# 🐱♟️ Ajedrez

Un juego de ajedrez en Java con una interfaz gráfica moderna y funcional.

## 🚀 Características Implementadas
- **Sistema de Piezas Completo**
  - Todas las piezas tradicionales (Peón, Torre, Caballo, Alfil, Reina, Rey)
  - Movimientos validados según las reglas del ajedrez
  - Captura de piezas implementada
  - Sistema de turnos alternados entre blancas y negras

- **Interfaz Gráfica**
  - Tablero visual con colores modernos (beige y marrón)
  - Piezas representadas con símbolos Unicode
  - Resaltado visual de piezas seleccionadas
  - Ventana de juego de 600x600 píxeles

- **Funcionalidades Adicionales**
  - Sistema de registro de movimientos (logging)
  - Detección de fin de partida
  - Interfaz de usuario intuitiva con selección por clic
  - Validación de movimientos legales
  - Sistema de guardado de partidas

## 🛠️ Requisitos
- Java Runtime Environment (JRE) 8 o superior
- Sistema operativo con soporte para Java AWT/Swing

## 📥 Instalación
1. Clona el repositorio:
   ```bash
   git clone https://github.com/koi89/chess
   ```
2. Navega al directorio del proyecto:
   ```bash
   cd chess
   ```
3. Compila el proyecto:
   ```bash
   javac src/*.java
   ```
4. Ejecuta el juego:
   ```bash
   java -cp src App
   ```

## 🎮 Cómo Jugar
1. Las blancas siempre mueven primero
2. Haz clic en una pieza para seleccionarla
3. Haz clic en el destino deseado para mover la pieza
4. El juego alternará automáticamente entre blancas y negras
5. Las piezas capturadas se eliminan del tablero
6. El juego termina cuando un rey es capturado

## 🐱 Nota
Los gatos mencionados en el título son solo decorativos en el README 😸

## 📝 Licencia
Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.