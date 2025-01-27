
int i = 0;
int j = 10;

void setup() {
  Serial.begin(9600);
}

/**
 * [ES]
 * Bucle principal del programa. Envía los valores de las variables "i" y "j" a través de la comunicación serial.
 *
 * [EN]
 * Main program loop. Sends the values of variables "i" and "j" via serial communication.
 */
void loop() {
  /*
    [ES]
    Puedes reemplazar el valor de las variables "i" y "j" por los valores de los sensores
    cuyos datos se desean enviar a Java para su graficación.

    [EN]
    You can replace the values of variables "i" and "j" with the sensor values
    that you want to send to Java for plotting.
  */
  Serial.println(i);
  Serial.println(j);
  i++;
  j++;
  delay(1000);
}

