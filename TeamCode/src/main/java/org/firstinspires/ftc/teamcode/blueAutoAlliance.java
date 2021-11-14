package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.*;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

@Autonomous(name="blueAutoAlliance", group="Pushbot")
public class blueAutoAlliance extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    DcMotor br,bl,fr,fl;
    HardwarePushbot robot   = new HardwarePushbot();
    int level,pos;
    private DistanceSensor sns;
    static final double     COUNTS_PER_MOTOR_REV    = 384.5 ;
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;
    static final double     WHEEL_DIAMETER_INCHES   = 4.72441 ;
    private static final String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite";
    private static final String[] LABELS = {"Ball", "Cube", "Duck", "Marker"};
    static final double     COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    private static final String VUFORIA_KEY ="AehNbRD/////AAABmfrJFCAhs0szi7am+mKXTa4q3y07exlAequzbbwNsWJfOsQSQsqI+nIm5E7xrcIbtGy8055JjV4o4eBUyAOiz6lqfmbHf46WfTcevDnaWZlZrXFGMtPeWhgbxwSDsdssiRPA6y0R46oiGkYiS2cDAUU/p/V2A5Av/3LeW7g/0Dz8Ovd0StPhLWaREMqXBQ3Su+lhqC3MrHq4nTSgRTPK7wLsRL7rDQgkgHaust/XWS+m4jo2Ihu/VmyxwacNaoePv91bWEdb8lfam145JVQ0z0Ep2lTM4GlE5Lh6uK90Td3mvKq0B/pjMFefGTnwt7C3xPv3qPdAOz3+imC3HR7BYvTY7oNgqsXO9sh0OQAFBrH1";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    public void grab(){

    }
    public void Init(){
        initVuforia();
        initTfod();
        if (tfod != null) {
            tfod.activate();
            // The TensorFlow software will scale the input images from the camera to a lower resolution.
            // This can result in lower detection accuracy at longer distances (> 55cm or 22").
            // If your target is at distance greater than 50 cm (20") you can adjust the magnification value
            // to artificially zoom in to the center of image.  For best results, the "aspectRatio" argument
            // should be set to the value of the images used to create the TensorFlow Object Detection model
            // (typically 16/9).
            tfod.setZoom(2.5, 16.0/9.0);
        }
        robot.init(hardwareMap);
        pos=36;
    }
    public void encoderDrive(double speed, double FLD, double FRD, double BLD, double BRD, double timeoutS) {
        int newFLTarget;
        int newFRTarget;
        int newBLTarget;
        int newBRTarget;

        if (opModeIsActive()) {
            newFLTarget = fl.getCurrentPosition() + (int)(FLD * COUNTS_PER_INCH);
            newFRTarget = fr.getCurrentPosition() + (int)(FRD * COUNTS_PER_INCH);
            newBLTarget = bl.getCurrentPosition() + (int)(BLD * COUNTS_PER_INCH);
            newBRTarget = br.getCurrentPosition() + (int)(BRD * COUNTS_PER_INCH);
            fl.setTargetPosition(newFLTarget);
            fr.setTargetPosition(newFRTarget);
            bl.setTargetPosition(newBLTarget);
            br.setTargetPosition(newBRTarget);
            fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            // reset the timeout time and start motion.
            runtime.reset();
            fl.setPower(speed);
            fr.setPower(speed);
            bl.setPower(speed);
            br.setPower(speed);
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (fl.isBusy() || fr.isBusy() || bl.isBusy() || br.isBusy() ))
            {
                telemetry.addData("motor pwr:", "%.2f", speed);
                telemetry.update();
            }

            // Stop all motion;
            fl.setPower(0);
            fr.setPower(0);
            bl.setPower(0);
            br.setPower(0);

            // Turn off RUN_TO_POSITION
            fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //sleep(250);   // optional pause after each move
        }
    }
    public void turnRight(){
        encoderDrive(0.6,12,-12,12,-12,1);
    }
    public void turnLeft(){
        encoderDrive(0.6,-12,12,-12,12,1);
    }
    public void lift(){
        if(level==1){

        }else if(level==2){

        }else{

        }
    }
    public void carousel(){

    }
    public void moveStraight(double spd,double dis,double time){
        encoderDrive(spd,dis,dis,dis,dis,dis/15.0);
    }
    private void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 320;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
    }
    @Override
    public void runOpMode() {
        Init();
        waitForStart();
        while(opModeIsActive()){
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if(updatedRecognitions != null){
                level=2;
            }else{
                moveStraight(0.6,11,1);
                pos-=6;
                if(updatedRecognitions != null){
                    level=3;
                }
                else{
                    level=1;
                }
            }
            int temp=pos-12;
            turnRight();
            moveStraight(0.7,temp,temp/17.0);
            carousel();
            moveStraight(0.7,-48,2);
            turnLeft();
            moveStraight(0.65,24,1.5);
            lift();
            moveStraight(0.65,-24,1.5);
            turnRight();
            moveStraight(0.7,48,2);
            turnLeft();
            moveStraight(0.65,24,1.5);
        }



    }

}
