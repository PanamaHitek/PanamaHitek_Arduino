void setup() {
  Serial.begin(9600);
}

/**
 * [ES]
 * Bucle principal del programa. Lee los datos disponibles en el puerto serie,
 * los convierte a un número de punto flotante, calcula su raíz cuadrada y
 * envía el resultado de vuelta al puerto serie.
 *
 * [EN]
 * Main program loop. Reads available data from the serial port, converts it
 * to a floating-point number, calculates its square root, and sends the
 * result back to the serial port.
 */

void loop() {
  if (Serial.available() > 0) {
    String str = Serial.readString();
    double root = sqrt(str.toDouble());
    Serial.println(root);
  }
}
