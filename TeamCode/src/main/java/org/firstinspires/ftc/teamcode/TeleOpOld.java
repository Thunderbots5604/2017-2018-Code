package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
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

@TeleOp(name="TeleOpJudge", group="TeleOp")
public class TeleOpOld extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime flipTime = new ElapsedTime();
    private ElapsedTime blockTime = new ElapsedTime();

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        boolean quarterSpeed = false;
        double quarterMultiplier = .25;

        boolean stall = false;

        DcMotor leftMotorFront = null;
        DcMotor leftMotorBack = null;

        DcMotor rightMotorFront = null;
        DcMotor rightMotorBack = null;

        OpticalDistanceSensor ods = null;
        TouchSensor touch = null;
        TouchSensor relictouch = null;
        ColorSensor color = null;
        GyroSensor gyroSensor = null;

        Servo blockright = null;
        Servo blockleft = null;

        DcMotor relicarm = null;
        DcMotor blockarm = null;

        blockright = hardwareMap.servo.get("block_right");
        blockleft = hardwareMap.servo.get("block_left");

        blockarm = hardwareMap.dcMotor.get("block_arm");
        relicarm = hardwareMap.dcMotor.get("relic_arm");

        leftMotorFront = hardwareMap.dcMotor.get("left_drive_front");
        leftMotorBack = hardwareMap.dcMotor.get("left_drive_back");

        rightMotorFront = hardwareMap.dcMotor.get("right_drive_front");
        rightMotorBack = hardwareMap.dcMotor.get("right_drive_back");

        ods = hardwareMap.opticalDistanceSensor.get("ods");
        touch = hardwareMap.touchSensor.get("touch");
        relictouch = hardwareMap.touchSensor.get("relic_touch");
        color = hardwareMap.colorSensor.get("color");
        gyroSensor = hardwareMap.get(ModernRoboticsI2cGyro.class, "gyro_sensor");

        rightMotorFront.setDirection(DcMotor.Direction.REVERSE);
        rightMotorBack.setDirection(DcMotor.Direction.REVERSE);
        leftMotorFront.setDirection(DcMotor.Direction.REVERSE);
        leftMotorBack.setDirection(DcMotor.Direction.REVERSE);
        blockarm.setDirection(DcMotor.Direction.REVERSE);

        ods.enableLed(true);
        color.enableLed(true);
        gyroSensor.calibrate();

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            boolean blockIn = ods.getLightDetected() > .05;

            //Stuff to display for Telemetry
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Quarter Speed:", quarterSpeed);
            telemetry.addData("Stalling? :", stall);
            telemetry.addData("Calibrated: ", gyroSensor.isCalibrating());
            telemetry.addData("Heading", gyroSensor.getHeading());
            telemetry.addData("X", gyroSensor.rawX());
            telemetry.addData("Y", gyroSensor.rawY());
            telemetry.addData("Z", gyroSensor.rawZ());
            telemetry.addData("Gyro Sensor: ", gyroSensor.status());
            telemetry.addData("Red", color.red());
            telemetry.addData("Blue", color.blue());
            telemetry.addData("ODS", "Light: " + ods.getLightDetected());
            telemetry.addData("Block In: ", blockIn);
            telemetry.addData("Quarter Speed:", quarterSpeed);
            telemetry.addData("Arm Touch: ", touch.isPressed());
            telemetry.addData("Relic Touch: ", relictouch.isPressed());
            telemetry.addData("Left Front", "Ticks: " + leftMotorFront.getCurrentPosition());
            telemetry.addData("Left Back", "Ticks: " + leftMotorBack.getCurrentPosition());
            telemetry.addData("Right Front", "Ticks: " + rightMotorFront.getCurrentPosition());
            telemetry.addData("Right Back", "Ticks: " + rightMotorBack.getCurrentPosition());

            telemetry.update();

            if(gamepad1.left_trigger != 0) {
                 quarterSpeed = true;
            }
            else {
                quarterSpeed = false;
            }

            //Forward and backward moving method
            if (gamepad1.left_stick_y != 0 && gamepad1.left_stick_x == 0 && gamepad1.right_stick_x == 0 && !quarterSpeed) {
                rightMotorFront.setPower(-gamepad1.left_stick_y);
                rightMotorBack.setPower(-gamepad1.left_stick_y);

                leftMotorFront.setPower(gamepad1.left_stick_y);
                leftMotorBack.setPower(gamepad1.left_stick_y);
            } else if (gamepad1.left_stick_y < 0 && gamepad1.left_stick_x > 0 && gamepad1.right_stick_x == 0 && !quarterSpeed) {
                rightMotorFront.setPower(-gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x));
                rightMotorBack.setPower(-gamepad1.left_stick_y);

                leftMotorFront.setPower(gamepad1.left_stick_y);
                leftMotorBack.setPower(gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x));
            } else if (gamepad1.left_stick_y == 0 && gamepad1.left_stick_x != 0 && gamepad1.right_stick_x == 0 && !quarterSpeed) {
                leftMotorFront.setPower(-gamepad1.left_stick_x);
                rightMotorFront.setPower(-gamepad1.left_stick_x);

                leftMotorBack.setPower(gamepad1.left_stick_x);
                rightMotorBack.setPower(gamepad1.left_stick_x);
            } else if (gamepad1.left_stick_y > 0 && gamepad1.left_stick_x > 0 && gamepad1.right_stick_x == 0 && !quarterSpeed) {
                rightMotorFront.setPower(-gamepad1.left_stick_y);
                rightMotorBack.setPower(-gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x));

                leftMotorFront.setPower(gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x));
                leftMotorBack.setPower(gamepad1.left_stick_y);
            } else if (gamepad1.left_stick_y > 0 && gamepad1.left_stick_x < 0 && gamepad1.right_stick_x == 0 && !quarterSpeed) {
                rightMotorFront.setPower(-gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x));
                rightMotorBack.setPower(-gamepad1.left_stick_y);

                leftMotorFront.setPower(gamepad1.left_stick_y);
                leftMotorBack.setPower(gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x));
            } else if (gamepad1.left_stick_y < 0 && gamepad1.left_stick_x < 0 && gamepad1.right_stick_x == 0 && !quarterSpeed) {
                rightMotorFront.setPower(-gamepad1.left_stick_y);
                rightMotorBack.setPower(-gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x));

                leftMotorFront.setPower(gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x));
                leftMotorBack.setPower(gamepad1.left_stick_y);
            } else if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x > 0 && !quarterSpeed) {
                leftMotorFront.setPower(gamepad1.left_stick_y);
                leftMotorBack.setPower(gamepad1.left_stick_y);

                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            } else if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x < 0 && !quarterSpeed) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);

                rightMotorFront.setPower(-gamepad1.left_stick_y);
                rightMotorBack.setPower(-gamepad1.left_stick_y);
            } else if (gamepad1.right_stick_x > 0 && !quarterSpeed) {
                leftMotorFront.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));
                leftMotorBack.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));

                rightMotorFront.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));
                rightMotorBack.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));
            } else if (gamepad1.right_stick_x < 0 && !quarterSpeed) {
                leftMotorFront.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));
                leftMotorBack.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));

                rightMotorFront.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));
                rightMotorBack.setPower(-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x));
            }

            //Forward and backward moving method
            else if (gamepad1.left_stick_y != 0 && gamepad1.left_stick_x == 0 && gamepad1.right_stick_x == 0 && quarterSpeed) {
                rightMotorFront.setPower(quarterMultiplier * -gamepad1.left_stick_y);
                rightMotorBack.setPower(quarterMultiplier * -gamepad1.left_stick_y);

                leftMotorFront.setPower(quarterMultiplier * gamepad1.left_stick_y);
                leftMotorBack.setPower(quarterMultiplier * gamepad1.left_stick_y);
            } else if (gamepad1.left_stick_y < 0 && gamepad1.left_stick_x > 0 && gamepad1.right_stick_x == 0 && quarterSpeed) {
                rightMotorFront.setPower(quarterMultiplier * (-gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x)));
                rightMotorBack.setPower(quarterMultiplier * -gamepad1.left_stick_y);

                leftMotorFront.setPower(quarterMultiplier * gamepad1.left_stick_y);
                leftMotorBack.setPower(quarterMultiplier * gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x));
            } else if (gamepad1.left_stick_y == 0 && gamepad1.left_stick_x != 0 && gamepad1.right_stick_x == 0 && quarterSpeed) {
                leftMotorFront.setPower(quarterMultiplier * -gamepad1.left_stick_x);
                rightMotorFront.setPower(quarterMultiplier * -gamepad1.left_stick_x);

                leftMotorBack.setPower(quarterMultiplier * gamepad1.left_stick_x);
                rightMotorBack.setPower(quarterMultiplier * gamepad1.left_stick_x);
            } else if (gamepad1.left_stick_y > 0 && gamepad1.left_stick_x > 0 && gamepad1.right_stick_x == 0 && quarterSpeed) {
                rightMotorFront.setPower(quarterMultiplier * -gamepad1.left_stick_y);
                rightMotorBack.setPower(quarterMultiplier * (-gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x)));

                leftMotorFront.setPower(quarterMultiplier * (gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x)));
                leftMotorBack.setPower(quarterMultiplier * gamepad1.left_stick_y);
            } else if (gamepad1.left_stick_y > 0 && gamepad1.left_stick_x < 0 && gamepad1.right_stick_x == 0 && quarterSpeed) {
                rightMotorFront.setPower(quarterMultiplier * (-gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x)));
                rightMotorBack.setPower(quarterMultiplier * -gamepad1.left_stick_y);

                leftMotorFront.setPower(quarterMultiplier * gamepad1.left_stick_y);
                leftMotorBack.setPower(quarterMultiplier * (gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x)));
            } else if (gamepad1.left_stick_y < 0 && gamepad1.left_stick_x < 0 && gamepad1.right_stick_x == 0 && quarterSpeed) {
                rightMotorFront.setPower(quarterMultiplier * -gamepad1.left_stick_y);
                rightMotorBack.setPower(quarterMultiplier * (-gamepad1.left_stick_y - Math.abs(gamepad1.left_stick_x)));

                leftMotorFront.setPower(quarterMultiplier * (gamepad1.left_stick_y + Math.abs(gamepad1.left_stick_x)));
                leftMotorBack.setPower(quarterMultiplier * gamepad1.left_stick_y);
            } else if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x > 0 && quarterSpeed) {
                leftMotorFront.setPower(quarterMultiplier * gamepad1.left_stick_y);
                leftMotorBack.setPower(quarterMultiplier * gamepad1.left_stick_y);

                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            } else if (gamepad1.left_stick_y != 0 && gamepad1.right_stick_x < 0 && quarterSpeed) {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);

                rightMotorFront.setPower(quarterMultiplier * -gamepad1.left_stick_y);
                rightMotorBack.setPower(quarterMultiplier * -gamepad1.left_stick_y);
            } else if (gamepad1.right_stick_x > 0 && quarterSpeed) {
                leftMotorFront.setPower(quarterMultiplier * (-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x)));
                leftMotorBack.setPower(quarterMultiplier * (-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x)));

                rightMotorFront.setPower(quarterMultiplier * (-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x)));
                rightMotorBack.setPower(quarterMultiplier * (-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x)));
            } else if (gamepad1.right_stick_x < 0 && quarterSpeed) {
                leftMotorFront.setPower(quarterMultiplier * (-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x)));
                leftMotorBack.setPower(quarterMultiplier * (-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x)));

                rightMotorFront.setPower(quarterMultiplier * (-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x)));
                rightMotorBack.setPower(quarterMultiplier * (-gamepad1.right_stick_x * Math.abs(gamepad1.right_stick_x) * Math.abs(gamepad1.right_stick_x)));
            }
            else {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }

            if(blockIn && !gamepad1.a) {
                blockleft.setPosition(.55);
                blockright.setPosition(.35);
            }
            else if (blockIn && gamepad1.a) {
                blockleft.setPosition(.95);
                blockright.setPosition(.1);
            }

            if(Math.abs(leftMotorFront.getCurrentPosition()) == 1680 || Math.abs(rightMotorBack.getCurrentPosition()) == 1680) {
                stall = true;
            }
            else {
                stall = false;
            }
            if (gamepad1.dpad_left ) {
                relicarm.setPower(-1);
            } else if (gamepad1.dpad_right && (relictouch.isPressed() == false)) {
                relicarm.setPower(1);
            } else {
                relicarm.setPower(0);
            }

            if (gamepad1.dpad_up || gamepad2.dpad_up) {
                blockarm.setPower(1);
            } else if ((gamepad1.dpad_down || gamepad2.dpad_down) && (touch.isPressed() == false)) {
                blockarm.setPower(-1);
            } else {
                blockarm.setPower(0);
            }
        }
    }
}

