//
//  KitchenHomeViewController.swift
//  NeighborCater
//
//  Created by Avinash Jain on 9/17/16.
//  Copyright © 2016 Avinash Jain. All rights reserved.
//

import UIKit
import Firebase



class KitchenHomeViewController: UIViewController {

    @IBOutlet weak var foodName: UITextField!
    
    @IBOutlet weak var foodDescription: UITextField!
    
    @IBOutlet weak var price: UITextField!
    
    var kitchenUID = String()
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func post(sender: AnyObject) {
        let ref = FIRDatabase.database().reference().child("kitchens").child("\(kitchenUID)")
        let kitchen : [String: AnyObject] = ["foodName": foodName.text!,
                                             "foodDescription": foodDescription.text!,
                                             "price": price.text!,
                                             ]
        ref.updateChildValues(kitchen)
        //performSegueWithIdentifier("kitchen", sender: self)
        print("Added item!")
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
