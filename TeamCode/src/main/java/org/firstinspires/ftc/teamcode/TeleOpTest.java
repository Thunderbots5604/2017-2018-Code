package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.lang.Math;

@TeleOp(name="TeleOp", group="TeleOp")
public class TeleOpTest extends LinearOpMode {

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

        ods.enableLed(true);
        color.enableLed(true);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
/*            boolean isFlat = gyro.getRotationFraction() < .2;*/
            boolean blockIn = ods.getLightDetected() > .05;

            hit.setPosition(.6);
            lever.setPosition(1.0);

            //Stuff to display for Telemetry
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Ultrasonic Sensor: ", ultra.getUltrasonicLevel());
            telemetry.addData("Red", color.red());
            telemetry.addData("Blue", color.blue());
            telemetry.addData("Block In: ", blockIn);/*
            telemetry.addData("Gyro Rotation: ", gyro.getRotationFraction());
            telemetry.addData("Is level: ", isFlat);*/
            telemetry.addData("Touched: ", touch.isPressed());
            /*
            telemetry.addData("Left Front", "Ticks: " + leftMotorFront.getCurrentPosition());
            telemetry.addData("Left Back", "Ticks: " + leftMotorBack.getCurrentPosition());
            telemetry.addData("Right Front", "Ticks: " + rightMotorFront.getCurrentPosition());
            telemetry.addData("Right Back", "Ticks: " + rightMotorBack.getCurrentPosition());*/
            telemetry.update();

            //Forward and backward moving method
            if(gamepad1.left_stick_y != 0 && gamepad1.left_stick_x == 0 && gamepad1.right_stick_x == 0) {
                rightMotorFront.setPower(-gamepad1.left_stick_y);
                rightMotorBack.setPower(-gamepad1.left_stick_y);

                leftMotorFront.setPower(gamepad1.left_stick_y);
                leftMotorBack.setPower(gamepad1.left_stick_y);
            }
            else if(gamepad1.left_stick_y < 0 && gamepad1.left_stick_x > 0 && gamepad1.right_stick_x == 0) {
                rightMotorFront.setPower(-gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x));
                rightMotorBack.setPower(-gamepad1.left_stick_y);

                leftMotorFront.setPower(gamepad1.left_stick_y);
                leftMotorBack.setPower(gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x));
            }
            else if (gamepad1.left_stick_y == 0 && gamepad1.left_stick_x != 0 && gamepad1.right_stick_x == 0) {
                leftMotorFront.setPower(-gamepad1.left_stick_x);
                rightMotorFront.setPower(-gamepad1.left_stick_x);

                leftMotorBack.setPower(gamepad1.left_stick_x);
                rightMotorBack.setPower(gamepad1.left_stick_x);
            }
            else if(gamepad1.left_stick_y > 0 && gamepad1.left_stick_x > 0 && gamepad1.right_stick_x == 0) {
                rightMotorFront.setPower(-gamepad1.left_stick_y);
                rightMotorBack.setPower(-gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x));

                leftMotorFront.setPower(gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x));
                leftMotorBack.setPower(gamepad1.left_stick_y);
            }
            else if(gamepad1.left_stick_y > 0 && gamepad1.left_stick_x < 0 && gamepad1.right_stick_x == 0) {
                rightMotorFront.setPower(-gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x));
                rightMotorBack.setPower(-gamepad1.left_stick_y);

                leftMotorFront.setPower(gamepad1.left_stick_y);
                leftMotorBack.setPower(gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x));
            }
            else if(gamepad1.left_stick_y < 0 && gamepad1.left_stick_x < 0 && gamepad1.right_stick_x == 0) {
                rightMotorFront.setPower(-gamepad1.left_stick_y);
                rightMotorBack.setPower(-gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x));

                leftMotorFront.setPower(gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x));
                leftMotorBack.setPower(gamepad1.left_stick_y);
            }
            else if (gamepad1.left_stick_y !=0  && gamepad1.right_stick_x > 0) {
                leftMotorFront.setPower(gamepad1.left_stick_y);
                leftMotorBack.setPower(gamepad1.left_stick_y);

                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }
            else if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x < 0) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);

                rightMotorFront.setPower(-gamepad1.left_stick_y);
                rightMotorBack.setPower(-gamepad1.left_stick_y);
            }
            else if(gamepad1.right_stick_x > 0) {
                leftMotorFront.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));
                leftMotorBack.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));

                rightMotorFront.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));
                rightMotorBack.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));
            }
            else if(gamepad1.right_stick_x < 0) {
                leftMotorFront.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));
                leftMotorBack.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));

                rightMotorFront.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));
                rightMotorBack.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));
            }
            else {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }

            if(gamepad1.dpad_up || gamepad2.dpad_up) {
                arm.setPower(-1);
            }
            else if ((gamepad1.dpad_down || gamepad2.dpad_down) && (touch.isPressed() == false)) {
                arm.setPower(1);
            }
            else {
                arm.setPower(0);
            }

            if(gamepad2.left_bumper && !gamepad2.right_bumper && gamepad2.right_trigger == 0) {
                spinleft.setPower(-1.0);
                spinright.setPower(0);
            }
            else if (gamepad2.left_bumper && gamepad2.right_bumper && gamepad2.right_trigger == 0) {
                spinleft.setPower(-1.0);
                spinright.setPower(1.0);
            }
            else if (gamepad2.left_bumper && !gamepad2.right_bumper && gamepad2.right_trigger != 0) {
                spinleft.setPower(-1.0);
                spinright.setPower(-1.0);
            }
            else if (gamepad2.left_trigger != 0 && !gamepad2.right_bumper && gamepad2.right_trigger == 0) {
                spinleft.setPower(1.0);
                spinright.setPower(0);
            }
            else if (gamepad2.left_trigger != 0 && gamepad2.right_bumper && gamepad2.right_trigger == 0) {
                spinleft.setPower(1.0);
                spinright.setPower(1.0);
            }
            else if (gamepad2.left_trigger != 0 && !gamepad2.left_bumper && gamepad2.right_trigger != 0) {
                spinleft.setPower(1.0);
                spinright.setPower(-1.0);
            }
            else if (gamepad2.right_bumper && !gamepad2.left_bumper && gamepad2.left_trigger == 0) {
                spinleft.setPower(0);
                spinright.setPower(1.0);
            }
            else if (gamepad2.right_trigger != 0 && !gamepad2.left_bumper && gamepad2.left_trigger == 0) {
                spinleft.setPower(0);
                spinright.setPower(-1.0);
            }
            else {
                spinleft.setPower(0);
                spinright.setPower(0);
            }
            //.6 for hit for middle
        }
    }
}

