int i = 0;
int j = 10;
int k = 100;
int l = 1000;
void setup() {
  Serial.begin(9600);
}

/*
 * [EN]
 * Main loop function that sends the values of variables `i`, `j`, `k`, and `l` to the serial port.
 *
 * [ES]
 * Función principal del bucle que envía los valores de las variables `i`, `j`, `k` y `l` al puerto serial.
 */

void loop() {

/*
    [ES]
    Puedes reemplazar el valor de las variables "i", "j", "k" y "l" con los valores de los sensores
    cuyos datos se desean enviar a Java para su graficación.

    [EN]
    You can replace the values of variables "i", "j", "k", and "l" with the sensor values
    that you want to send to Java for plotting.
*/

  Serial.println(i);
  Serial.println(j);
  Serial.println(k);
  Serial.println(l);
  i++;
  j++;
  k++;
  l++;
  delay(1000);
}

