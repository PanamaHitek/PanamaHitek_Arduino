/**
 * [ES]
 * Configuración inicial del programa. Configura la velocidad de comunicación serial y el pin 13 como salida.
 *
 * [EN]
 * Initial setup of the program. Sets the serial communication speed and configures pin 13 as output.
 */
void setup() {
  Serial.begin(9600);
  pinMode(13, OUTPUT);
}

/**
 * [ES]
 * Bucle principal del programa. Lee los datos disponibles en el puerto serie y controla el LED en el pin 13
 * según el mensaje recibido ("on" para encender, "off" para apagar).
 *
 * [EN]
 * Main program loop. Reads available data from the serial port and controls the LED on pin 13
 * based on the received message ("on" to turn on, "off" to turn off).
 */
void loop() {
  if (Serial.available() > 0) {
    String str = Serial.readString();
    if (str == "on") {
      digitalWrite(13, HIGH);
    } else if (str == "off") {
      digitalWrite(13, LOW);
    }
  }
}