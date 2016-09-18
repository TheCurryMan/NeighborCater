//
//  SignUpViewController.swift
//  NeighborCater
//
//  Created by Avinash Jain on 9/17/16.
//  Copyright © 2016 Avinash Jain. All rights reserved.
//

import UIKit
import Firebase
import FirebaseAuth


class SignUpViewController: UIViewController {
    
    @IBOutlet var username: UITextField!
    
    @IBOutlet var email: UITextField!
    
    @IBOutlet var password: UITextField!
    
    @IBOutlet weak var number: UITextField!
    
    var ref: FIRDatabaseReference!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        ref = FIRDatabase.database().reference()
        // Do any additional setup after loading the view.
    }
    
    override func viewDidAppear(animated: Bool) {
        
        //try! FIRAuth.auth()!.signOut()
        
        
        if let user = FIRAuth.auth()?.currentUser {
            print("User signed in")
            
            self.performSegueWithIdentifier("home", sender: self)
            // User is signed in.
        } else {
            // No user is signed in.
            print("Please sign in!")
        }
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
        
    }
    
    @IBAction func signup(sender: AnyObject) {
        if let em = email.text, pass = password.text, name = username.text, num = number.text{
            FIRAuth.auth()?.createUserWithEmail(email.text!, password: password.text!) {(user, error) in
                if let error = error {
                    print(error.localizedDescription)
                }
                else {
                    print("User signed in!")
                    
                    self.ref.child("users").updateChildValues(["\(FIRAuth.auth()!.currentUser!.uid)":["name": name, "email": em, "number": num]])
                    
                    self.performSegueWithIdentifier("home", sender: self)
                }
            } }
        else{
            print("You left email/password empty")
        }
    }
    
    /*
     // MARK: - Navigation
     
     // In a storyboard-based application, you will often want to do a little preparation before navigation
     override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
     // Get the new view controller using segue.destinationViewController.
     // Pass the selected object to the new view controller.
     }
     */
    
}

