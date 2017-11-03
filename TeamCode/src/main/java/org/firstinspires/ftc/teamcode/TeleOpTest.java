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
import java.lang.Math;

@TeleOp(name="Testing", group="TeleOp")
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

        DcMotor push = null;

        leftMotorFront = hardwareMap.dcMotor.get("left_drive_front");
        leftMotorBack = hardwareMap.dcMotor.get("left_drive_back");

        rightMotorFront = hardwareMap.dcMotor.get("right_drive_front");
        rightMotorBack = hardwareMap.dcMotor.get("right_drive_back");

        arm = hardwareMap.dcMotor.get("arm");

        push = hardwareMap.dcMotor.get("push");

        rightMotorFront.setDirection(DcMotor.Direction.REVERSE);
        rightMotorBack.setDirection(DcMotor.Direction.REVERSE);
        leftMotorFront.setDirection(DcMotor.Direction.REVERSE);
        leftMotorBack.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {

            //Stuff to display for Telemetry
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();

            //Forward and backward moving method
            if(gamepad1.left_stick_y != 0 && gamepad1.left_stick_x < .1 && gamepad1.left_stick_x > -.1) {
                leftMotorFront.setPower(gamepad1.left_stick_y);
                leftMotorBack.setPower(gamepad1.left_stick_y);

                rightMotorFront.setPower(-gamepad1.left_stick_y);
                rightMotorBack.setPower(-gamepad1.left_stick_y);

                telemetry.addLine("Moving forward or Backward");
                telemetry.update();
            }
            //Turning methods in TeleOp
            else if(gamepad1.right_stick_x > 0) {
                leftMotorFront.setPower(-gamepad1.right_stick_x);
                leftMotorBack.setPower(-gamepad1.right_stick_x);

                rightMotorFront.setPower(-gamepad1.right_stick_x);
                rightMotorBack.setPower(-gamepad1.right_stick_x);
            }
            else if(gamepad1.right_stick_x < 0) {
                leftMotorFront.setPower(-gamepad1.right_stick_x);
                leftMotorBack.setPower(-gamepad1.right_stick_x);

                rightMotorFront.setPower(-gamepad1.right_stick_x);
                rightMotorBack.setPower(-gamepad1.right_stick_x);
            }

            //Strafe Method
            else if (gamepad1.left_stick_x != 0 && gamepad1.left_stick_y < .1 && gamepad1.left_stick_y > -.1) {
                rightMotorBack.setPower(gamepad1.left_stick_x);
                rightMotorFront.setPower(-gamepad1.left_stick_x);

                leftMotorFront.setPower(-gamepad1.left_stick_x);
                leftMotorBack.setPower(gamepad1.left_stick_x);
            }

            //Diagonal Strafe
            else if(gamepad1.left_stick_x > .1 && gamepad1.left_stick_y < -.1) {
                leftMotorFront.setPower(-gamepad1.left_stick_x);
                rightMotorBack.setPower(gamepad1.left_stick_x);

                leftMotorBack.setPower(0);
                rightMotorFront.setPower(0);
            }
            else if(gamepad1.left_stick_x < -.1 && gamepad1.left_stick_y < -.1) {
                leftMotorFront.setPower(0);
                rightMotorBack.setPower(0);

                leftMotorBack.setPower(gamepad1.left_stick_x);
                rightMotorFront.setPower(-gamepad1.left_stick_x);
            }

            else if(gamepad1.left_stick_x > .1 && gamepad1.left_stick_y > .1) {
                leftMotorFront.setPower(0);
                rightMotorBack.setPower(0);

                leftMotorBack.setPower(gamepad1.left_stick_x);
                rightMotorFront.setPower(-gamepad1.left_stick_x);
            }
            else if(gamepad1.left_stick_x < -.1 && gamepad1.left_stick_y > .1) {
                leftMotorFront.setPower(-gamepad1.left_stick_x);
                rightMotorBack.setPower(gamepad1.left_stick_x);

                leftMotorBack.setPower(0);
                rightMotorFront.setPower(0);
            }
            else {
                leftMotorFront.setPower(0);
                leftMotorBack.setPower(0);
                rightMotorFront.setPower(0);
                rightMotorBack.setPower(0);
            }

            if(gamepad1.dpad_up) {
                arm.setPower(.25);
            }
            else if (gamepad1.dpad_down) {
                arm.setPower(-.25);
            }
            else {
                arm.setPower(0);
            }

            if(gamepad1.a || gamepad2.a) {
                push.setPower(1);
            }
            else if (gamepad1.b || gamepad2.b) {
                push.setPower(-1);
            }
            else {
                push.setPower(0);
            }
        }
    }
}
