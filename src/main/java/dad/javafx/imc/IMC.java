package dad.javafx.imc;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class IMC extends Application {
	private Label pesoLabel; //declarar las variables que vamos a usar.
	private Label kgLabel;
	private Label alturaLabel;
	private Label cmLabel;
	private Label IMCLabel;
	private Label resultadoLabel;
	private TextField pesotext;
	private TextField alturatext;
	private Label numLabel;
	
	private DoubleProperty peso = new SimpleDoubleProperty(); // con el doubleproperty cogemos el texto( los datos) de los textfield
	private DoubleProperty altura = new SimpleDoubleProperty();
	private DoubleProperty resultado = new SimpleDoubleProperty();

	@Override
	public void start(Stage primaryStage) throws Exception {
		pesoLabel = new Label("Peso");   //inicializar las variables para poder usarlas
		kgLabel = new Label("kg");
		alturaLabel = new Label("Altura");
		cmLabel = new Label("cm");
		IMCLabel = new Label("IMC :");
		resultadoLabel = new Label("-");
		numLabel = new Label();
		
		pesotext = new TextField(); //caja de texto y predefinimos el tamaño.
		pesotext.setPrefWidth(50);
		
		alturatext = new TextField();
		alturatext.setPrefWidth(50);
		
		HBox cajapeso = new HBox(); // cajita donde meter las cosas que queremos visualizar, siempre en orden de izq a derecha.
		cajapeso.getChildren().addAll(pesoLabel, pesotext, kgLabel);
		cajapeso.setSpacing(5);
		cajapeso.setAlignment(Pos.BASELINE_CENTER);
		
		HBox cajaaltura = new HBox();
		cajaaltura.getChildren().addAll(alturaLabel, alturatext, cmLabel);
		cajaaltura.setSpacing(5);
		cajaaltura.setAlignment(Pos.BASELINE_CENTER);
		
		HBox cajaresultado = new HBox();
		cajaresultado.getChildren().addAll(IMCLabel, numLabel);
		cajaresultado.setAlignment(Pos.BASELINE_CENTER);
		
		VBox root = new VBox();  // cajita donde meter las cosas que queremos visualizar, siempre en orden de arriba a abajo.
		root.getChildren().addAll(cajapeso, cajaaltura, cajaresultado, resultadoLabel);
		root.setSpacing(5);
		root.setAlignment(Pos.BASELINE_CENTER);
		
		Scene scene = new Scene(root, 320, 200); // Creamos la escena(ventana) en la que se va a mostrar lo de las cajas.
		primaryStage.setTitle("IMC.fxml");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		Bindings.bindBidirectional(pesotext.textProperty(),peso, new NumberStringConverter());
		Bindings.bindBidirectional(alturatext.textProperty(),altura, new NumberStringConverter());
		
		// Pasar lo introducido a metros
				DoubleBinding alturaM = altura.divide(100);

				// Altura al cuadrado
				DoubleBinding alturaCuadrado = alturaM.multiply(alturaM);

				// Operación
				DoubleBinding res = peso.divide(alturaCuadrado);
				resultado.bind(res);
				numLabel.textProperty().bind(resultado.asString("%.2f")); /* el dato del textproperty (dato del label) lo enlazamos con el resultado que como es un num hay que pasarlo a string
																		  con dos decimales*/
				
				numLabel.textProperty().addListener((o,ov,nv)->{  // o = dato observable, ov = guarda el dato que estaba antes, nv = nuevo dato
					double n = res.doubleValue();
					
					if (n < 18.5) {
						resultadoLabel.setText("Bajo Peso");
					}
					
					if (n >= 18.5 && n <25) {
						resultadoLabel.setText("Normalucho");
					}
					
					if (n >= 25 && n <30) {
						resultadoLabel.setText("Pavarotti");
					}
					
					if (n > 30) {
						resultadoLabel.setText("Jabba de hut");
					}
					
				});
				
	}

	public static void main(String[] args) { // en el main de esta clase siempre se pone launch args
		launch(args);

	}

}
// para ejecutarlo siempre se crea a parte una clase main, porque si no, pal carrer.