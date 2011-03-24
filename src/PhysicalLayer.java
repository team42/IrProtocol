import java.io.*;
import java.util.*;
import gnu.io.*;

/**
 * Class declaration
 *
 *
 * @author Lasse
 * 
 */
@SuppressWarnings("restriction")
public class PhysicalLayer implements SerialPortEventListener {
   private static final int            BAUDRATE = 2400;
   @SuppressWarnings("rawtypes")
   private static Enumeration	         portList;
   private static CommPortIdentifier   portId;
   private static SerialPort           serialPort;
   private static OutputStream         outputStream;
   private static InputStream          inputStream;
   private LinkLayer link = null;

   /**
    * Constructs a new SerialFrame instance and opens the port given in the
    * argument port.
    * 
    * If an initial attempt to attach a listener succeeds,
    * subsequent attempts will throw TooManyListenersException without
    * effecting the first listener.
    * 
    * @param port The port to use for communication (COM1, /dev/ttyUSB0, etc.)
    * @throws java.util.TooManyListenersException
    */
   //public PhysicalLayer(String port, LinkLayer linkL) throws TooManyListenersException {
   public PhysicalLayer(String port) throws TooManyListenersException {
      
	  //link = linkL;
	   
	  portList = CommPortIdentifier.getPortIdentifiers();
      while (portList.hasMoreElements()) {
         portId = (CommPortIdentifier) portList.nextElement();
         if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
            if (portId.getName().equals(port)) {
               try {
                  serialPort = (SerialPort) portId.open("", 2000);
                  serialPort.setSerialPortParams(
                        BAUDRATE,
                        SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);
                  serialPort.addEventListener(this); // Add event listener so we can react to data on the port
                  serialPort.notifyOnDataAvailable(true); // React on data available
                  //serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_XONXOFF_IN | SerialPort.FLOWCONTROL_XONXOFF_OUT);
                  //serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
                  //serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
                  inputStream = serialPort.getInputStream();
               } catch (PortInUseException e) {
                  e.printStackTrace();
               } catch (IOException e) {
                  e.printStackTrace();
               } catch (UnsupportedCommOperationException e) {
                  e.printStackTrace();
               }
            }
         }
      }
   }


   /**
    * Implementation of the SerialPortEventListener method.
    * This method receives data from the serial port and sends the received
    * byte Array in a FrameEvent to a FrameEventListener that has registered
    * using addFrameEventListener.
    * The <code>FrameProtocol</code> is specified using the Strategy design pattern.
    * @param event Event object.
    */
   public void serialEvent(SerialPortEvent event) {
      switch(event.getEventType()) {
         case SerialPortEvent.DATA_AVAILABLE:
            if(inputStream != null) {
               byte[] readBuffer = new byte[1];

               try {
                  while (inputStream.available() > 0) {
                     inputStream.read(readBuffer);

                     //link.receiver(new String(new byte[] {readBuffer[0]}));
                     System.out.print(new String(new byte[] {readBuffer[0]})); // System.out is too slow
                     //System.out.println(new String(new byte[] {readBuffer[0]})); // System.out is too slow
                  }
               } catch (IOException e) {
                  e.printStackTrace();
               }
               //closePort();
            }
            break;
         default:
            System.out.println("EVENT TYPE: " + event.getEventType());
            break;
      }      
   }

   /**
    * Sends a string as bytes to the serial port.
    * 
    * @param data the string to be sent
    */
   public synchronized void transmit(String data) {

      //Debug output
      System.out.println("Transmit: " + data + "\n]");
      try {
         outputStream = serialPort.getOutputStream();
         outputStream.write(data.getBytes());
         outputStream.write(10); // Binary Terminator (\n = 10)
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public void closePort() {
      serialPort.close();
   }

}