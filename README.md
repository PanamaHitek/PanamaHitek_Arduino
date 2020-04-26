Librería PanamaHitek_Arduino, versión 3.1.0

Por Antony García González

*Ingeniero Electromecánico e Investigador para la Universidad Tecnológica de Panamá. Fundador del sitio web htp://panamahitek.com junto al Panama Hitek Creative Team.*

- **email:** antony.garcia.gonzalez@gmail.com
- **whatsapp:** +50767347398
- **facebook:** http://facebook.com/panamahitek
- **twitter:** @panamahitek

La librería incluye 3 clases principales. 
- La clase **PanamaHitek_Arduino** es la encargada de manejar todas las conexiones y la comunicación con Arduino.
- La clase **PanamaHitek_MultiMessage** incluye las herramientas necesarias para recibir múltiples mensajes de forma simultánea en Java.
- La clase **PanamaHitek_DataBuffer** almacena datos de forma ordenada, permite la visualización en una tabla y la exportación de datos a MS Excel (archivo .xlsx)

Adicionalmente cuenta con 4 clases dedicadas a la gestión de gráficos en tiempo real, a través de la utilización de las dependencias de JFreeCharts.
- **PanamaHitek_DualDialChart** permite hacer graficas tipo reloj analógico con dos agujas
- **PanamaHitek_SingleDialChart** permite hacer graficas tipo reloj analógico con una aguja
- **PanamaHitek_ThermometerChart** permite hacer graficas tipo termómetro
- **PanamaHitek_TimeLineChart** permite hacer gráficas de línea de múltiples datos en función del tiempo

Desde la versión 2.8.0 se ha descontinuado el uso de RXTX en favor de Java Simple Serial Connector (Alexey Sokolov, 
https://github.com/scream3r/java-simple-serial-connector)

Versión 3.0.0
---------------------
La nueva y mejorada version de la librería. Se ha agregado nuevos recursos que permiten almacenar datos recibidos y exportarlos en hojas de cálculo de MS Excel o graficarlos en tiempo real con la ayuda de las librerías POI y JFreeCharts.
También se ha agregado ejemplos de uso de las principales características de la librería.

Video sobre utilización de la librería para tabulación de datos y exportar la información recibida a Excel:
https://www.youtube.com/watch?v=wo4ts0osZV8

Versión 2.8.1
---------------------
Se han corregido algunos bugs de la versión 2.8.0

Versión 2.8.0
---------------------
Con esta versión se produce un gran salto desde las versiones anteriores de esta librería. Hasta ahora se había utilizado la librería 
RXTX (https://github.com/rxtx/rxtx/tree/development/rxtxSerial-java) como Core para la comunicación serial. Ahora hemos migrado a la librería
Java Simple Serial Connector (JSSC) de Alexey Sokolov (https://github.com/scream3r/java-simple-serial-connector).

Este salto se ha producido debido a que esta compilación muestra un mejor desempeño que el Core anterior. Es por esta razón que otros proyectos
como el propio Arduino IDE (escrito en Java) que antes utilizaban RXTX ahora han migrado a JSSC. El cambio de librería en el IDE de Arduino se produjo
desde la versión 1.5.6 BETA, lanzada en Github el 20 de febrero de 2014.

Con JSSC no es necesario instalar los drivers para la comunicación serial. Esto ya lo habíamos logrado nosotros desde la versión 2.7.2 de nuestra librería.
La migración a JSSC nos ha permitido lograr que nuestra librería sea compatible con sistemas operativos Linux, Mac y Solaris, además de Windows.

Cuando se ejecute un código que contenga esta librería, Java se encargará de buscar en la ruta C:/Users/nombre_de_usuario/.jssc/windows (en Windows) en donde 
debe estar el fichero jSSC-2.8_x86_64.dll, necesario para la comunicación entre Java y el puerto serie. Si el archivo no se encuentra disponible, Java creará 
la ruta y colocará el fichero necesario. Lo mismo aplica para otros sistemas operativos diferentes a Windows.

Si en el equipo en el cual utilizaremos esta librería se encuentra instalado el Arduino IDE, no será necesario que la librería instale el archivo, ya que el
que el Arduino IDE utiliza esta misma técnica.


Versión 2.7.3
---------------------
Corregidos algunos bugs de la versión 2.7.2


Versión 2.7.2
---------------------
Se ha agregado la compatibilidad con Maven. Ahora se puede llamar a la librería PanamaHitek_Arduino agregando las sigueintes líneas al archivo POM:
     
<dependencies>

        <dependency>
            <groupId>com.github.PanamaHitek</groupId>
            <artifactId>PanamaHitek_Arduino</artifactId>
            <version>2.7.2</version>
        </dependency>
    
</dependencies>

<repositories>

        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>

</repositories>

Versión 2.7.1
---------------------
Se ha corregido algunos pequeños errores en la estructura interna de la librería.

Versión 2.7.0
---------------------
Antes de esta versión era necesario tener instalados los drivers rxtxSerial.dll en la ruta de JAVA_HOME. 
A partir de la versión 2.7.0, en cada ejecución la librería verifica si los drivers están instalados en la ruta C:/JavaRXTX.
Si no existen dichos ficheros, la librería los crea. Si el directorio no existe, se encargar de armar la estructura para el almacenamiento
de los archivos *.dll (Windows) necesarios para que el programa cargue sin problemas.

Este feature SÓLO FUNCIONA EN WINDOWS. Invitamos a los usuarios de Linux a que nos ayuden a expandir estas capacidades a otras plataformas.
Nuestro repositorio en GitHub contiene todos los archivos de esta librería: https://github.com/PanamaHitek/Arduino-JavaAPI


Versión 2.6.0
---------------------
Grandes cambios. La librería ya no se llama Arduino para Java, sino que la hemos renombrado PanamaHitek_Arduino.
Se ha modificado algunos métodos. Procedo a detallar los cambios:

  -- Se ha aplicado el lower Camel Case a todos los métodos. Lo que antes llamábamos ArduinoRXTX ahora se cambió por arduinoRXTX.
  -- Se ha despreciado el método NameSerialPortsAt(). Ahora se utiliza getSerialPorts().
  -- Se ha despreciado el método SerialPortsAvailable(). Ahora se utiliza getPortsAvailable().
  -- Se renombró el método MessageAvailable() a isMessageAvailable().
  -- Se ha hecho innecesario el establecimiento del TimeOut como parámetro de entrada para los métodos ArduinoRX, ArduinoTX y ArduinoRXTX.
  -- Se ha documentado todos los métodos y clases con el JavaDoc.
  -- Se ha hecho público el código de la librería en nuestro repositorio de Github (https://github.com/PanamaHitek/Arduino-JavaAPI).
  

Incluye los nuevos métodos en la clase PanamaHitek_Arduino:
  -- List<String> getSerialPorts()
     Devuelve una lista con los dispositivos conectados en el Puerto Serie.
  -- int getPortsAvailable()
     Devuelve la cantidad de dispositivos conectados en el Puerto Serie.

La clase PanamaHitek_MultiMessage incluye un nuevo método:
  -- List<String> getMessageList()
     Entrega los mensajes recibidos como una Lista.

Versión 2.5.0
---------------------
Incluye los nuevos métodos en la clase Arduino:
  -- void ShowMessageDialogs(boolean input)
     Permite activar o desactivar las ventanas emergentes cuando se produce algún error en tiempo de ejecución

  -- void SendByte(int input)
     Envía Bytes a Arduino por medio del puerto Serie.

Versiones Anteriores
---------------------
Métodos incluidos en la librería, en la clase Arduino.
  -- void ArduinoTX(String PORT_NAME, int TIME_OUT, int DATA_RATE)
     Permite establecer una conexión entre Arduino y Java, donde sólo se puede enviar información de Java a Arduino
     por medio de comunicación serial.

  -- void ArduinoRX(String PORT_NAME, int TIME_OUT, int DATA_RATE, SerialPortEventListener evento)
     Permite establecer una conexión entre Arduino y Java, donde sólo se puede enviar información de Arduino a Java
     por medio de comunicación serial. Se requiere instanciar la clase SerialPortEventListener, de la librería RXTX.

  -- void ArduinoRXTX(String PORT_NAME, int TIME_OUT, int DATA_RATE, SerialPortEventListener evento)
     Permite establecer una conexión entre Arduino y Java, donde se puede enviar y recibir información entre Arduino y Java
     por medio de comunicación serial. Se requiere instanciar la clase SerialPortEventListener, de la librería RXTX.

  -- void SendData(String data)
     Permite enviar una cadena de caracteres desde Java hacia Arduino

  -- String ReceiveData()
     Permite recibir información directamente desde Arduino por medio de Comunicación Serial.

  -- boolean MessageAvailable()
     Devuelve true cuando se ha terminado de recibir un mensaje desde Arduino, utilizando Serial.println().
  
  -- String PrintMessage()
     Devuelve un string con el mensaje que se haya enviado desde Arduino, solamente cuando MessageAvailable() devuelva true.

  -- int SerialPortsAvailable()
     Devielve la cantidad de puertos serie disponibles y activos en la computadora

  -- String NameSerialPortAt(int index)
     Nombra los puertos serie disponibles

  -- void KillArduinoConnection()
     Finaliza la conexión entre Arduino y Java.

Para una documentación completa sobre este proyecto, visita:
http://panamahitek.com/libreria-arduino-para-java/
