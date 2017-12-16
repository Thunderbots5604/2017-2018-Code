package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import java.lang.Math;

@Autonomous(name="SpitOutTimeAutonomous", group="Autonomous")
public class AutoTest extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        DcMotor leftMotorFront = null;
        DcMotor leftMotorBack = null;

        DcMotor rightMotorFront = null;
        DcMotor rightMotorBack = null;

        CRServo spinleft = null;
        CRServo spinright = null;

        leftMotorFront = hardwareMap.dcMotor.get("left_drive_front");
        leftMotorBack = hardwareMap.dcMotor.get("left_drive_back");

        rightMotorFront = hardwareMap.dcMotor.get("right_drive_front");
        rightMotorBack = hardwareMap.dcMotor.get("right_drive_back");

        rightMotorFront.setDirection(DcMotor.Direction.REVERSE);
        rightMotorBack.setDirection(DcMotor.Direction.REVERSE);
        leftMotorFront.setDirection(DcMotor.Direction.REVERSE);
        leftMotorBack.setDirection(DcMotor.Direction.REVERSE);

/*        leftMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotorFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotorBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);*/

        spinleft = hardwareMap.crservo.get("spinleft");
        spinright = hardwareMap.crservo.get("spinright");

        waitForStart();
        runtime.reset();

        rightMotorFront.setPower(.5);
        rightMotorBack.setPower(.5);
        leftMotorFront.setPower(-.5);
        leftMotorBack.setPower(-.5);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < .95)) {
            telemetry.addLine("Moving forward");
            telemetry.update();
        }
        telemetry.addLine("Done ;)");
        telemetry.update();
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);

        sleep(1000);

        spinleft.setPower(1.0);
        spinright.setPower(-1.0);
        rightMotorFront.setPower(-.5);
        rightMotorBack.setPower(-.5);
        leftMotorFront.setPower(.5);
        leftMotorBack.setPower(.5);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < .1)) {
            telemetry.addLine("Spin Out");
            telemetry.update();
        }
        leftMotorFront.setPower(0);
        leftMotorBack.setPower(0);
        rightMotorFront.setPower(0);
        rightMotorBack.setPower(0);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < .4)) {
            telemetry.addLine("Just keep spinning");
            telemetry.update();
        }

        spinleft.setPower(0);
        spinright.setPower(0);

        telemetry.addLine("Done ;)");
        telemetry.update();


        sleep(1000);
    }
}
