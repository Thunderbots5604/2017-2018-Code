package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="RedAuto", group="Autonomous")
public class RedAuto extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        DcMotor leftMotorFront = null;
        DcMotor leftMotorBack = null;

        DcMotor rightMotorFront = null;
        DcMotor rightMotorBack = null;

        DcMotor arm = null;

        Servo lever = null;
        Servo hit = null;

        CRServo spinleft = null;
        CRServo spinright = null;

        OpticalDistanceSensor ods = null;/*
        Gyroscope gyro = null;*/
        TouchSensor touch = null;
        UltrasonicSensor ultra = null;
        ColorSensor color = null;

        leftMotorFront = hardwareMap.dcMotor.get("left_drive_front");
        leftMotorBack = hardwareMap.dcMotor.get("left_drive_back");

        rightMotorFront = hardwareMap.dcMotor.get("right_drive_front");
        rightMotorBack = hardwareMap.dcMotor.get("right_drive_back");

        arm = hardwareMap.dcMotor.get("arm");

        spinleft = hardwareMap.crservo.get("spinleft");
        spinright = hardwareMap.crservo.get("spinright");

        ods = hardwareMap.opticalDistanceSensor.get("ods");
/*        gyro = hardwareMap.gyroScope.get("gyro");*/
        touch = hardwareMap.touchSensor.get("touch");
        ultra = hardwareMap.ultrasonicSensor.get("ultra");
        color = hardwareMap.colorSensor.get("color");

        lever = hardwareMap.servo.get("lever");
        hit = hardwareMap.servo.get("hit");


        rightMotorFront.setDirection(DcMotor.Direction.REVERSE);
        rightMotorBack.setDirection(DcMotor.Direction.REVERSE);
        leftMotorFront.setDirection(DcMotor.Direction.REVERSE);
        leftMotorBack.setDirection(DcMotor.Direction.REVERSE);
        arm.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        runtime.reset();

        spinleft.setPower(0);
        spinright.setPower(0);

        hit.setPosition(.6);
        lever.setPosition(.3);

        spinleft.setPower(0);
        spinright.setPower(0);

        sleep(1000);
        while(color.red() == 0 || color.blue() == 0) {
            hit.setPosition(hit.getPosition() - .01);
            sleep(250);
        }

        spinleft.setPower(0);
        spinright.setPower(0);

        if(color.red() > (color.blue())) {

            telemetry.addLine("Is red");
            telemetry.update();
            sleep(1000);
            hit.setPosition(0);
        }
        else {
            telemetry.addLine("Is not Red");
            telemetry.update();
            sleep(1000);
            hit.setPosition(1);
        }
        sleep(1000);

        hit.setPosition(.6);
        lever.setPosition(1.0);

        sleep(1000);

        rightMotorFront.setPower(.25);
        rightMotorBack.setPower(.25);
        leftMotorFront.setPower(-.25);
        leftMotorBack.setPower(-.25);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2.5)) {
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
        rightMotorFront.setPower(-.25);
        rightMotorBack.setPower(-.25);
        leftMotorFront.setPower(.25);
        leftMotorBack.setPower(.25);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.0)) {
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

        hit.setPosition(.6);
        lever.setPosition(1.0);

        sleep(1000);

    }
}
