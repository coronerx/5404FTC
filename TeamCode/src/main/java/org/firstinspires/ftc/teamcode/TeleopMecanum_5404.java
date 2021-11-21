package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import org.firstinspires.ftc.teamcode.MecanumDrivetrain;




/*
	Holonomic concepts from:
	http://www.vexforum.com/index.php/12370-holonomic-drives-2-0-a-video-tutorial-by-cody/0
   Robot wheel mapping:
          X FRONT X
        X           X
      X  FL       FR  X
              X
             XXX
              X
      X  BL       BR  X
        X           X
          X       X
*/
@TeleOp(name = "TeleopMecanum5404", group = "Concept")
//@Disabled
public class TeleopMecanum_5404 extends OpMode {
    double pos=0;
    DcMotor motorFrontRight,motorFrontLeft,motorBackRight,motorBackLeft,lnrSld,crs,intake,intake1;
    Servo s;

    public MecanumDrivetrain drivetrain;

    public TeleopMecanum_5404() {

    }
    @Override
    public void init() {

        motorFrontRight = hardwareMap.dcMotor.get("fr");
        motorFrontLeft = hardwareMap.dcMotor.get("fl");
        motorBackLeft = hardwareMap.dcMotor.get("bl");
        motorBackRight = hardwareMap.dcMotor.get("br");
        intake=hardwareMap.dcMotor.get("intake");
        crs=hardwareMap.dcMotor.get("crs");
        intake1=hardwareMap.dcMotor.get("intake1");
        s=hardwareMap.servo.get("s");
        lnrSld=hardwareMap.dcMotor.get("linear");
        drivetrain = new MecanumDrivetrain(new DcMotor[]{motorFrontLeft, motorFrontRight, motorBackLeft, motorBackRight});
        pos=1;
        s.setPosition(0.7);
    }


    @Override
    public void loop() {
        double course = Math.atan2(-gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI/2;
        double velocity = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
        double rotation = -0.5 * gamepad1.right_stick_x;

        drivetrain.setCourse(course);
        drivetrain.setVelocity(0.5 * velocity);
        drivetrain.setRotation(rotation);
        if(gamepad1.left_bumper) {
            s.setPosition(0.7);
        }else if(gamepad1.right_bumper){
            s.setPosition(0);
        }
        if(gamepad2.left_bumper){
            lnrSld.setPower(0.5);
        }else if(gamepad2.right_bumper){
            lnrSld.setPower(-0.5);
        }else {
            lnrSld.setPower(0);
        }
        if(gamepad1.y){
            intake.setPower(-0.6);
            intake1.setPower(0.6);
        }else if(gamepad1.a){
            intake.setPower(0);
            intake1.setPower(0);
        }
        if(gamepad2.x) {
            crs.setPower(0.3);
        }else if(gamepad2.b){
            crs.setPower(-0.3);
        }else{
            crs.setPower(0);
        }

    }

    @Override
    public void stop() {

    }
    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }

}
