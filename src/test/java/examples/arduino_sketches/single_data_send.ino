int i = 0;
void setup() {
  Serial.begin(9600);
}

/**
 * [ES]
 * Bucle principal del programa. Envía el valor de la variable "i" a través de la comunicación serial.
 *
 * [EN]
 * Main program loop. Sends the value of the variable "i" via serial communication.
 */
void loop() {
  Serial.println(i);
  i++;
  delay(1000);
}

