package org.deeplearning4j.examples.dataexamples;

import org.datavec.api.io.labels.ParentPathLabelGenerator;
import org.datavec.api.records.listener.impl.LogRecordListener;
import org.datavec.api.split.FileSplit;
import org.datavec.image.loader.NativeImageLoader;
import org.datavec.image.recordreader.ImageRecordReader;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import static org.deeplearning4j.nn.conf.layers.DenseLayer.*;

public class FirstTest {
    private static Logger log = LoggerFactory.getLogger((FirstTest.class));

    public static void main(String[] args) throws IOException {
        int height = 52;        //img height
        int width = 51;         //img width
        int channels = 1;       //greyscale
        int rngseed =123;
        Random randNumGen = new Random(rngseed);
        int batchSize = 128;
        int hiddenLayers = 100;
        int outputNum = 43;     //possible labels
        int numEpochs = 1000;

        // file paths
        File trainData = new File ("C:\\Users\\Gamecube\\Desktop\\AIproject\\DataSet\\Training");
        File testData = new File ("C:\\Users\\Gamecube\\Desktop\\AIproject\\DataSet\\Testing");


        FileSplit train = new FileSplit(trainData, NativeImageLoader.ALLOWED_FORMATS, randNumGen);
        FileSplit test = new FileSplit(testData, NativeImageLoader.ALLOWED_FORMATS, randNumGen);

        // Parent path is the label
        ParentPathLabelGenerator labelMaker = new ParentPathLabelGenerator();

        // will conver img to height,width and greyscale if not already there
        ImageRecordReader recordReader = new ImageRecordReader(height, width, channels, labelMaker);

        //Initialize record reader and listen (must throw or try/catch exception
        recordReader.initialize(train);
        //recordReader.setListeners(new LogRecordListener());

        // dataset iterator
        DataSetIterator dataIter = new RecordReaderDataSetIterator(recordReader, batchSize, 1, outputNum);

        // scale pixel values to between 0..1
        DataNormalization scaler = new ImagePreProcessingScaler(0,1);
        scaler.fit(dataIter);
        dataIter.setPreProcessor(scaler);

        // run it over 3 images
        /*
        for (int i = 0; i < 3; i++) {
            DataSet ds = dataIter.next();
            System.out.println(ds);
            System.out.println(dataIter.getLabels());
        }
        */

        //build the neural network
        log.info("******Build Model******");

        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
            .seed(rngseed)
            .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
            .iterations(1)
            .learningRate(0.006)
            .updater(Updater.NESTEROVS)
            .regularization(true).l2(1e-4)
            .list()
            .layer(0, new DenseLayer.Builder()
                .nIn(height*width)
                .nOut(hiddenLayers)
                .activation(Activation.RELU)
                .weightInit(WeightInit.XAVIER)
                .build())
            .layer(1, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                .nIn(hiddenLayers)
                .nOut(outputNum)
                .activation(Activation.SOFTMAX)
                .weightInit(WeightInit.XAVIER)
                .build())
            .pretrain(false).backprop(true)
            .setInputType(InputType.convolutional(height, width, channels))
            .build();

        //use the model built
        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();

        model.setListeners(new ScoreIterationListener(10));

        log.info("******TRAIN MODEL******");

        for (int i = 0; i < numEpochs; i++) {
            model.fit(dataIter);
        }

        log.info("******TRAIN STATS*******");
        Evaluation evalTrain = new Evaluation(outputNum);

        while (dataIter.hasNext()) {
            DataSet next = dataIter.next();
            //compares model prediction to label for data
            INDArray outputTrain = model.output(next.getFeatures());
            evalTrain.eval(next.getLabels(), outputTrain);
        }

        log.info(evalTrain.stats());

        //save the model
        log.info("******SAVE TRAINED MODEL******");

        //where to save
        File locationToSave = new File("model6.zip");

        //auto push updates to the model
        boolean saveUpdater = false;

        ModelSerializer.writeModel(model, locationToSave, saveUpdater);



        log.info("******Evaluate Model******");

        recordReader.reset();

        recordReader.initialize(test);
        // pass to network to validate
        DataSetIterator testIter = new RecordReaderDataSetIterator(recordReader, batchSize, 1, outputNum);
        scaler.fit(testIter);
        testIter.setPreProcessor(scaler);

        // create eval object
        Evaluation evalTest = new Evaluation(outputNum);

        while(testIter.hasNext()) {
            DataSet next = testIter.next();
            //compares model prediction to label for data
            INDArray outputTest = model.output(next.getFeatures());
            evalTest.eval(next.getLabels(), outputTest);
        }
        log.info(evalTest.stats());

    }
}
