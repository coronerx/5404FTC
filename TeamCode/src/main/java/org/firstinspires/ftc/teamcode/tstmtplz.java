package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.*;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

@Autonomous(name="tstmtplz", group="Pushbot")
//@Disabled
public class tstmtplz extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    DcMotor br,bl,fr,fl;
    HardwarePushbot robot   = new HardwarePushbot();
    int level,pos;
    private DistanceSensor sns;
    static final double     COUNTS_PER_MOTOR_REV    = 384.5 ;
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;
    static final double     WHEEL_DIAMETER_INCHES   = 4.72441 ;
    static final double     COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * 3.1415);
    public void grab(){

    }
    public void Init(){
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
    public void thrown_gameElement(){

    }
    public void moveStraight(double spd,double dis,double time){
        encoderDrive(spd,dis,dis,dis,dis,dis/15.0);
    }
    @Override
    public void runOpMode() {
        Init();
        waitForStart();
        while(opModeIsActive()){

        }

    }

}
